package com.hframework.utils.scala

import java.util.TimeZone

import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.fasterxml.jackson.datatype.joda.JodaModule
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper

object JacksonScalaUtils {
  val mapper = new ObjectMapper() with ScalaObjectMapper
  mapper.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"))
  mapper.registerModule(DefaultScalaModule)
//  mapper.disable(MapperFeature.AUTO_DETECT_GETTERS)
//  mapper.disable(MapperFeature.AUTO_DETECT_IS_GETTERS)
//  mapper.enable(MapperFeature.AUTO_DETECT_FIELDS)
  mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
  mapper.registerModule(new JodaModule)

  def toJson(value: Map[Symbol, Any]): String = {
    toJson(value map { case (k, v) => k.name -> v })
  }

  def toJson(value: Any): String = {
    mapper.writeValueAsString(value)
  }

  def toMap[V](json: String)(implicit m: Manifest[V]) = fromJson[Map[String, V]](json)

  def fromJson[T](json: String)(implicit m: Manifest[T]): T = {
    mapper.readValue[T](json)

  }

  def fromJson[T](json: String, valueType: Class[T]): T = {
    mapper.readValue(json, valueType)

  }
}