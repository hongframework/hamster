package com.hframework.datacenter.hamster.worker

import java.util.Calendar._
import java.util.{Calendar, Date}

object Times {
  val delayMillis = 2 * 1000L
  val timeUnit = Array('s','m','h','d','M','y','w')
  val calendarFields = Array(SECOND, MINUTE, HOUR_OF_DAY, DAY_OF_WEEK, MONTH, YEAR, WEEK_OF_YEAR)

  def getDelayTimeMillis(delayMillis:Long = 0L):Long = System.currentTimeMillis() - delayMillis

  def getEndTime(expr: String): Calendar ={
    val cal = java.util.Calendar.getInstance()
    cal.setTime(new Date(getDelayTimeMillis(delayMillis)))
    cal.set(java.util.Calendar.MILLISECOND, 0)
    cal.setFirstDayOfWeek(java.util.Calendar.MONDAY)

    val unit = expr.last
    val unitIndex = timeUnit.indexOf(unit)
    val addVal = expr.init.toInt
    for(i <- 1 to unitIndex) cal.set(calendarFields(i-1), 0)
    cal.add(SECOND, -1)

    cal.add(calendarFields(unitIndex), if(addVal < 0) addVal + 1 else addVal)
    cal
  }

  def getBeginTime(expr: String, lessEqualThanMillis: Long = -1): Calendar ={
    val cal = java.util.Calendar.getInstance()
    cal.setTime(new Date(getDelayTimeMillis(delayMillis)))
    cal.set(java.util.Calendar.MILLISECOND, 0)
    cal.setFirstDayOfWeek(java.util.Calendar.MONDAY)

    val unit = expr.last
    val unitIndex = timeUnit.indexOf(unit)
    val addVal = expr.init.toInt
    for(i <- 1 to unitIndex) cal.set(calendarFields(i-1), 0)
    cal.add(calendarFields(unitIndex), if(addVal > 0) addVal - 1 else addVal)
    while (lessEqualThanMillis > 0 && cal.getTimeInMillis > lessEqualThanMillis + 1000) {//如果超过一分钟误差，需要向前追数
      cal.add(calendarFields(unitIndex), -1)
    }
    cal
  }

}
