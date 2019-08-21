package com.hframework.utils.scala

import java.io._

import org.codehaus.jackson.JsonParser
import org.codehaus.jackson.map.ObjectMapper
import org.json4s._
import org.json4s.jackson.JsonMethods._

object JsonScalaUtils {

  private val objectMapper: ObjectMapper = new ObjectMapper

//  objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
  objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)

  /**
    * Json内容转化为对象
    *
    * @param content
    * @param valueType
    * @return
    * @throws IOException
    */
  @throws[IOException]
  def readValue[T] (content: String, valueType: Class[_] *): T = {
    return objectMapper.readValue (content, objectMapper.getTypeFactory.constructParametricType (valueType (0), valueType (1) ) )
  }

  @throws[IOException]
  def readValue[T] (content: String, valueType: Class[T] ): T = {
    return objectMapper.readValue (content, valueType)
  }

  @throws[IOException]
  def writeValueAsString[T] (t: JValue): String = {
    type DslConversion = T => JValue
    compact(render(t))
  }
}
