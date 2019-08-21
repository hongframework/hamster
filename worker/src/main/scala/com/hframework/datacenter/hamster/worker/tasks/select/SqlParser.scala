package com.hframework.datacenter.hamster.worker.tasks.select

import com.hframework.utils.scala.Logging

import scala.util.matching.Regex

object SqlParser{

  val SEGMENT_TAG = "SEG_"
  val TERM_TAG = ";"
  val TERM_STR = s" $TERM_TAG  "
  val OUT_QUOTE_R = "(?<=\\().+(?=\\))".r
  val INNER_QUOTE_R = "[^\\(\\)]+".r
  val SQL_KEY_WORDS = s"\\s((select)|(from)|(where)|(limit)|(group\\s+by)|(order\\s+by)|(having)" +
    s"|(SELECT)|(FROM)|(WHERE)|(LIMIT)|(GROUP\\s+BY)|(ORDER\\s+BY)|(HAVING)|($TERM_TAG))\\s"
  val SQL_SELECT_R = "(?<=(select|SELECT)\\s).+?(?=\\s(from|FROM)\\s)".r
  val SQL_FROM_R = s"(?<=\\s(from|FROM)\\s).+?(?=$SQL_KEY_WORDS)".r
  val SQL_WHERE_R = s"(?<=\\s(where|WHERE)\\s).+?(?=$SQL_KEY_WORDS)".r
  val SQL_ORDER_BY_R = s"(?<=\\s((order\\sby)|(ORDER\\sBY))\\s).+?(?=$SQL_KEY_WORDS)".r
  val SQL_GROUP_BY_R = s"(?<=\\s((group\\sby)|(GROUP\\sBY))\\s).+?(?=$SQL_KEY_WORDS)".r
  val SQL_LIMIT_R = s"(?<=\\s(limit|LIMIT)\\s).+?(?=$SQL_KEY_WORDS)".r
  val SEGMENT_R = s"$SEGMENT_TAG\\d+".r


  val JOIN_R = "(LEFT|left)?(RIGTH|right)?\\s(JOIN|join)\\s".r
  val ON_R = "\\s(ON|on)\\s".r
  val BETWEEN_R = "\\s(BETWEEN|between)\\s".r
  val AND_R = "\\s(AND|and)\\s".r
  val AND_OR_R = "\\s(AND|and|OR|or)\\s".r
//  val VAR_R = "\\w+\\.\\w+".r
  val VAR_R = "[0-9a-zA-Z_\\$\\{\\}]+\\.\\w+".r //由于在配置视图中使用了分表表达式，因此进行扩充

  val AS_R = "\\s(AS|as)\\s".r

  val BETWEEN_FLAG = " _BETWEEN_ "
  val AND_FLAG = " _AND_ "

  var regexs = Map.empty[Int, Regex]


  def parseParameters(sql: String): List[(String, String, String)] = {
    val parameters = SqlParser.VAR_R.findAllIn(sql)
    val parameterKVs = parameters.map(parm => {
      (parm.split("\\.").head, parm.split("\\.").last, parm)
    })
    parameterKVs.toList
  }

  def parse(sql: String):SqlSegment = {
    val trimSql = sql.replaceAll("\r\n"," ").trim
    val cleanSql = if(trimSql.endsWith(";")) trimSql.substring(0, trimSql.length - 1) else trimSql
    val root = SqlSegment(cleanSql)
    root
  }

  def getRegex(times: Int): Regex = {
    if(regexs.get(times).isEmpty) {
      regexs += (times -> powerRegex("[^\\(\\)]+","\\(","\\)", times).r)
    }
    return regexs.get(times).get
  }

  def powerRegex(base: String, left: String, right: String, times: Int): String = {
    var nexContent = base
    var regex = base
    for( i <- Array.range(0, times)) {
      regex = s"$left$nexContent$right"
      nexContent = s"($base$regex$base)+"
    }
    println(regex)
    regex
  }

  def getOutQuotes(sql: String): List[String] = {
    val map = sql.toCharArray.zipWithIndex.filter(ci => ci._1 == '(' || ci._1 == ')')
    var quoteCnt = 0
    val cntMap = map.map(ci => {
      if(ci._1 == '(') quoteCnt += 1
      else if(ci._1 == ')') quoteCnt -= 1
      (ci._1, ci._2, quoteCnt)
    })
    val filterMap = cntMap.filter(cic => cic._3 == 0)
    var cursor = 0
    val result = filterMap.map(cic => {
      val seq = sql.substring(sql.indexOf("(", cursor), cic._2 + 1)
      cursor = cic._2 + 1
      seq
    })

    result.toList
  }

  @Deprecated
  def findOutQuote(sql: String): List[String] = {
    var times = 0
    import util.control.Breaks._
    breakable{
      while (true) {
        val found = getRegex(times + 1).findFirstIn(sql).isDefined
        if(found) {
          times += 1
        }else {
          break
        }
      }
    }

    val children = if(times == 0) Nil else getRegex(times).findAllIn(sql)
    children.toList
  }
}

class SqlParser {

}

case class SqlSegment(sql: String)  extends Logging {
  def addOrderBy(tmpSql: String): Unit = {
//    logger.info(s"add order by => $tmpSql")
    val parameters = SqlParser.parseParameters(tmpSql)
    addOrderBy(parameters, tmpSql)
  }

  def addSelect(tmpSql: String): Unit = {
    logger.info(s"add select => $tmpSql")
    val parameters = SqlParser.parseParameters(tmpSql)
    addSelect(parameters, tmpSql)
  }

  def addLimit(tmpSql: String): Unit = {
    logger.info(s"add limit => $tmpSql")
    if(fromSegment.isDefined){
      if(limitSegment.isDefined){
        limitSegment.get.add(tmpSql)
      }else {
        limitSegment = Some(LimitSegment(this, tmpSql, tmpSql, Nil, true))
      }
    }
  }

  def addLimit(start: Int, end: Int =  -1): Unit = {
    val tmpSql = s"$start ${if(end > 0){", " + end} else ""}"
    addLimit(tmpSql)
  }

  def addWhere(tmpSql: String): Unit = {
    logger.info(s"add where => $tmpSql")
    val parameters = SqlParser.parseParameters(tmpSql)
    addWhere(parameters, tmpSql)
  }

  def addSelect(parameters: List[(String, String, String)], addSql: String): Unit ={
    val tmpTables = parameters.map(_._1).distinct
    if(fromSegment.isDefined){
      val fromTables = fromSegment.get.tables.map(_._1)
      if(tmpTables.find(tTable => !fromTables.contains(tTable)).isDefined) {
        logger.warn(s"this need support, ${fromTables.mkString("[",", ","]")} ${tmpTables.mkString("[",", ","]")}" )
      }else {
        val sql = parameters.foldLeft(addSql)((tmpSql, param) => {
          val tableInfo = fromSegment.get.tables.find(_._1 == param._1)
          tmpSql.replace(param._3, param._3.replace(param._1 + ".", tableInfo.get._2.get + "."))
        })
        if(selectSegment.isDefined){
          selectSegment.get.add(sql)
        }else {
          logger.error("this should not exists !")
        }
      }
    }
  }

  def addOrderBy(parameters: List[(String, String, String)], addSql: String): Unit ={
    val tmpTables = parameters.map(_._1).distinct
    if(fromSegment.isDefined){
      val fromTables = fromSegment.get.tables.map(_._1)
      if(tmpTables.find(tTable => !fromTables.contains(tTable)).isDefined) {
        logger.warn(s"this need support, ${fromTables.mkString("[",", ","]")} ${tmpTables.mkString("[",", ","]")}" )
      }else {
        val sql = parameters.foldLeft(addSql)((tmpSql, param) => {
          val tableInfo = fromSegment.get.tables.find(_._1 == param._1)
          tmpSql.replace(param._3, param._3.replace(param._1 + ".", tableInfo.get._2.get + "."))
        })
        if(orderBySegment.isDefined){
          orderBySegment.get.add(sql)
        }else {
          orderBySegment = Some(OrderBySegment(this, sql, sql, Nil, true))
        }
      }
    }
  }

  def addWhere(parameters: List[(String, String, String)], addSql: String): Unit ={
    val tmpTables = parameters.map(_._1).distinct
    if(fromSegment.isDefined){
      val fromTables = fromSegment.get.tables.map(_._1)
      val transSql =if(tmpTables.find(tTable => !fromTables.contains(tTable)).isDefined) {
        logger.warn(s"this need support, ${fromTables.mkString("[",", ","]")} ${tmpTables.mkString("[",", ","]")}" )
        addSql
      }else {
        parameters.foldLeft(addSql)((tmpSql, param) => {
          val tableInfo = fromSegment.get.tables.find(_._1 == param._1)
          tmpSql.replace(param._3, param._3.replace(param._1 + ".", tableInfo.get._2.get + "."))
        })
      }
      if(whereSegment.isDefined){
        whereSegment.get.add(transSql)
      }else {
        whereSegment = Some(WhereSegment(this, transSql, transSql, Nil, true))
      }
    }
  }

  def runtimeSql: String = {
    var result = sql
    result = if(whereSegment.isDefined) {
      if(whereSegment.get.autoAdd) {
        result.replace(fromSegment.get.sql, s"${fromSegment.get.sql} WHERE ${whereSegment.get.finalSql} ")
      }else {
        result.replace(whereSegment.get.sql, whereSegment.get.finalSql)
      }
    }else result

    result = if(selectSegment.isDefined) {
      result.replace(selectSegment.get.sql, selectSegment.get.finalSql)
    }else result

    result = if(orderBySegment.isDefined) {
      if(orderBySegment.get.autoAdd) {
        if(limitSegment.isDefined && limitSegment.get.autoAdd) {
          result +  s" ORDER BY ${orderBySegment.get.finalSql} "
        }else {
          result.replace(limitSegment.get.sql, s" ORDER BY ${orderBySegment.get.finalSql} ${limitSegment.get.sql}")
        }
      }else {
        result.replace(orderBySegment.get.sql, orderBySegment.get.finalSql)
      }
    }else result

    result = if(limitSegment.isDefined) {
      if(limitSegment.get.autoAdd) {
        result +  s" LIMIT ${limitSegment.get.finalSql} "
      }else {
        result.replace(limitSegment.get.sql, limitSegment.get.finalSql)
      }
    }else result



    result
  }

  val children = SqlParser.getOutQuotes(sql).map(seg => seg.substring(1, seg.length - 1))
  val childrenSegments: List[SqlSegment] = children.map(SqlSegment(_))
  val replaceSql = childrenSegments.zipWithIndex.foldLeft(sql)((tmpSql, childInfo) => {
    val startIndex = tmpSql.indexOf(childInfo._1.sql)
    tmpSql.substring(0, startIndex) + SqlParser.SEGMENT_TAG + childInfo._2 + tmpSql.substring(startIndex + childInfo._1.sql.length)
  })

//  println("replaceSql = " + replaceSql)

  val tempSql = replaceSql.concat(SqlParser.TERM_STR)
  val selectSql = SqlParser.SQL_SELECT_R.findFirstIn(tempSql)
  val fromSql = SqlParser.SQL_FROM_R.findFirstIn(tempSql)
  val whereSql = SqlParser.SQL_WHERE_R.findFirstIn(tempSql)
  val orderBySql = SqlParser.SQL_ORDER_BY_R.findFirstIn(tempSql)
  val groupBySql = SqlParser.SQL_GROUP_BY_R.findFirstIn(tempSql)
  val limitSql = SqlParser.SQL_LIMIT_R.findFirstIn(tempSql)

  val selectSegment = if(selectSql.isDefined){
    val vals = initSegmentParameters(selectSql.get)
    Some(SelectSegment(this, vals._1, vals._2, vals._3))
  }  else None

  val fromSegment = if(fromSql.isDefined) {
    val vals = initSegmentParameters(fromSql.get)
    Some(FromSegment(this, vals._1, vals._2, vals._3))
  } else None
  var whereSegment = if(whereSql.isDefined) {
    val vals = initSegmentParameters(whereSql.get)
    Some(WhereSegment(this, vals._1, vals._2, vals._3))
  } else None
  var orderBySegment = if(orderBySql.isDefined) {
    val vals = initSegmentParameters(orderBySql.get)
    Some(OrderBySegment(this, vals._1, vals._2, vals._3))
  } else None
  val groupBySegment = if(groupBySql.isDefined) {
    val vals = initSegmentParameters(groupBySql.get)
    Some(GroupBySegment(this, vals._1, vals._2, vals._3))
  } else None
  var limitSegment = if(limitSql.isDefined) {
    val vals = initSegmentParameters(limitSql.get)
    Some(LimitSegment(this, vals._1, vals._2, vals._3))
  } else None

  def initSegmentParameters(replaceSql: String): (String, String, List[SqlSegment]) = {
    var thisChildrenSegments = List.empty[SqlSegment]
    val sql = SqlParser.SEGMENT_R.findAllIn(replaceSql).foldLeft(replaceSql)((tmpSql, segment) =>{
      val childSegment = childrenSegments(segment.substring(SqlParser.SEGMENT_TAG.length).toInt)
      thisChildrenSegments = childSegment :: thisChildrenSegments
      tmpSql.replace(segment, childSegment.sql)
    })
//    println(replaceSql)
    (replaceSql, sql, thisChildrenSegments)
  }
}

case class SelectSegment(container: SqlSegment, replaceSql: String, sql: String, childrenSegments: List[SqlSegment]) extends Logging{
  var finalSql = sql
  def add(addSql: String) = {
    finalSql = finalSql + ", " + addSql + " "
    println(finalSql)
  }

  val pairs = replaceSql.split(",")
//  pairs.map(pairs => {
//    val tableAlias = tableAliasString.split("\\s")
//    (tableAlias(0).trim, if(tableAlias.isDefinedAt(1)) Some(tableAlias(1).trim) else None, Nil)
//  })
  logger.info(s"SelectSegment[$replaceSql][$sql]")
}

case class FromSegment(container: SqlSegment, replaceSql: String, sql: String, childrenSegments: List[SqlSegment]) extends Logging{
  val isJoinStruct = SqlParser.JOIN_R.findFirstIn(replaceSql).isDefined
  val tables = if(isJoinStruct) {
    val pairs = replaceSql.split(SqlParser.JOIN_R.regex)
    pairs.map(pair => {
      val items = pair.split(SqlParser.ON_R.regex)
      val tableAliasString = items.head.trim
      val tableAlias = tableAliasString.split("\\s+")
      (tableAlias(0).trim, if(tableAlias.isDefinedAt(1)) Some(tableAlias(1).trim) else None, items.tail)
    })
  }else {
    val pairs = replaceSql.replace(SqlParser.AS_R.regex, " ").split(",")
    pairs.map(tableAliasString => {
      val tableAlias = tableAliasString.trim.split("\\s+")
      (tableAlias(0).trim, if(tableAlias.isDefinedAt(1)) Some(tableAlias(1).trim) else None, Nil)
    })
  }
  logger.info(s"FromSegment[${tables.map(t => (t._1, t._2.getOrElse(""))).mkString(";")}][$replaceSql][$sql]")
}

case class WhereSegment(container: SqlSegment, replaceSql: String, sql: String, childrenSegments: List[SqlSegment], autoAdd: Boolean = false) extends Logging{

  var finalSql = sql
  def add(addSql: String) = {
    finalSql = s"($addSql) AND $finalSql "
//    finalSql = finalSql + " AND (" + addSql + ")"

    println(finalSql)
  }

  val pairs = {
    val betweens = SqlParser.BETWEEN_R.findAllIn(replaceSql)
    val replaceBetweenAndSql = betweens.foldLeft(replaceSql)((tmpSql, betweenWord) => {
      val betweenIndex = tmpSql.indexOf(betweenWord)
      tmpSql.substring(0, betweenIndex) + SqlParser.BETWEEN_FLAG +
        SqlParser.AND_R.replaceFirstIn(tmpSql.substring(betweenIndex + betweenWord.length), SqlParser.AND_FLAG)
    })
    val pairs = SqlParser.AND_OR_R.findAllIn(replaceBetweenAndSql)
      .map(_.replaceAll(SqlParser.BETWEEN_FLAG, " BETWEEN ").replaceAll(SqlParser.AND_FLAG, " AND "))
    pairs
  }
//  val parameters = SqlParser.VAR_R.findAllIn(replaceSql)
//  val parameterKVs = SqlParser.parseParameters(replaceSql)

  logger.info(s"WhereSegment[$replaceSql][$sql]")

}

case class OrderBySegment(container: SqlSegment, replaceSql: String, sql: String, childrenSegments: List[SqlSegment], autoAdd: Boolean = false) extends Logging {
  var finalSql = sql
  def add(sql: String) = {
    finalSql = sql + ", " + finalSql
  }
}

case class GroupBySegment(container: SqlSegment, replaceSql: String, sql: String, childrenSegments: List[SqlSegment], autoAdd: Boolean = false) extends Logging

case class LimitSegment(container: SqlSegment, replaceSql: String, sql: String, childrenSegments: List[SqlSegment], autoAdd: Boolean = false) extends Logging {
  var finalSql = sql
  def add(sql: String) = {
    finalSql = sql
  }

}

