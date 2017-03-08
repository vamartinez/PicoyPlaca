package com.victor

/**
  * Created by vic on 7/3/2017.
  */

import org.joda.time.{DateTime, Interval}

class Predictor {

  val FIRST_PROHIBITION_INTERVAL = (new DateTime(1, 1, 1, 7, 0), new DateTime(1, 1, 1, 9, 30))
  val SECOND_PROHIBITION_INTERVAL = (new DateTime(1, 1, 1, 16, 0), new DateTime(1, 1, 1, 19, 30))

  /**
    * return true when the vehicle can road
    *
    * @param plate
    * @param date
    * @param time
    * @return Boolean
    */
  def canRoad(plate: String, date: String, time: DateTime): Boolean = {
    try {
      plate.reverse.head.toString.toInt match {
        case 0 => !((5 == getDayOfWeek(date)) && cantRoadTime(time))
        case x: Int => !(((x + 1) / 2 == getDayOfWeek(date)) && cantRoadTime(time))
      }
    } catch {
      case err: CustomException => throw err
      case _: Throwable => throw CustomException("bad plate string")
    }

  }

  /**
    * check just the interval time
    *
    * @param time
    * @return boolean if is valid or not
    */
  private def cantRoadTime(time: DateTime): Boolean = {
    val overrideTimeWithCustomDate = new DateTime(1, 1, 1, time.getHourOfDay, time.getMinuteOfHour)
    new Interval(FIRST_PROHIBITION_INTERVAL._1, FIRST_PROHIBITION_INTERVAL._2).contains(overrideTimeWithCustomDate) ||
      new Interval(SECOND_PROHIBITION_INTERVAL._1, SECOND_PROHIBITION_INTERVAL._2).contains(overrideTimeWithCustomDate)
  }


  /** *
    * get day of week 1 => monday, 7 => sunday
    *
    * @param date
    * @return Int
    */
  private def getDayOfWeek(date: String): Int = {
    try {
      val splitDate = date.split("-")
      new DateTime(splitDate(0).toInt, splitDate(1).toInt, splitDate(2).toInt, 1, 1).getDayOfWeek
    } catch {
      case _: Throwable => throw CustomException("bad date string")
    }

  }
}

case class CustomException(msg: String) extends Exception