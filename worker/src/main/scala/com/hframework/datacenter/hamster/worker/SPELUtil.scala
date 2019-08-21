package com.hframework.datacenter.hamster.worker

import org.springframework.expression.spel.standard.SpelExpressionParser

object SPELUtil {
  def invokeExpress(expr: String, vars: List[String], data: Map[String, String],
                    parser: SpelExpressionParser = new SpelExpressionParser): String = {
    if(vars.filterNot(data.contains(_)).isEmpty) {
      val result = vars.foldLeft(expr)((exp, var1) => exp.replaceAll(var1,data.get(var1).get))
      try{
        parser.parseExpression(result).getValue(classOf[String])
      }catch {
        case e: Exception =>{
          val errorMsg = e.getMessage
          val bigNoOp = "'[0-9]+'".r.findFirstIn(errorMsg)
          if(bigNoOp.isDefined){
            val bigNo = bigNoOp.get.substring(1,bigNoOp.get.length -1)
            val retryExpr = result.replaceAll(bigNo, bigNo + "l")
            parser.parseExpression(retryExpr).getValue(classOf[String])
          }else throw new RuntimeException(s"express = $expr, vars = ${vars.mkString(",")}, " +
            s"data = ${data.mkString(",")}, resultExpress = [$result], ${e.getMessage}")
        }
      }
    }else
      throw new RuntimeException(s"express = $expr, vars = ${vars.mkString(",")}, " +
        s"data = ${data.mkString(",")} can't be invoke !")

  }

  def parseExpress(expr: String): Option[(String, List[String])] = {
    if(expr == null) None else {
      val express = expr.trim
      if(express.matches("\\$\\{[^\\{\\}]+\\}")){
        val trimExpress = express.substring(2, express.length - 1)
        val varExpress = trimExpress.replaceAll("'[_a-zA-Z]\\w*'", "")
        val vars = "[_a-zA-Z]\\w*".r.findAllIn(varExpress).toList.sorted.reverse
        Some(trimExpress, vars)
      }else None
    }
  }

  def fetchExpress(str: String): Option[(String, List[String])] = {
    if(str == null) None else {
      val trimStr = str.trim
      val expOp = "\\$\\{[^\\{\\}]+\\}".r.findFirstIn(trimStr)
      if(expOp.isDefined){
        Some(expOp.get, parseExpress(expOp.get).get._2)
      }else None
    }
  }
}
