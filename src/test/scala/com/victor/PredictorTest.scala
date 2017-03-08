package com.victor

/**
  * Created by vic on 7/3/2017.
  */

import org.joda.time.DateTime
import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfter

/**
  * Created by vic on 7/3/2017.
  */


class PredictorTest extends FunSuite with BeforeAndAfter {

  var predictor: Predictor = _

  before {
    predictor = new Predictor
  }

  test("test all valid's day  of the week (from tuesday to sunday  for plate that finish in 1 ) on a bad time") {
    List.range(7, 12).map { day =>
      assert(predictor.canRoad("PBA-821", "2017-3-" + day.toString, new DateTime(2017, 7, 3, 8, 25)))
    }
  }

  test("test all bad hours in a invalid day") {
    List(7, 8, 9, 16, 17, 18, 19).map(hour => assert(!predictor.canRoad("PBA-123", "2017-03-7", new DateTime(2017, 7, 3, hour, 25))))
  }

  test("test good hours in a invalid day") {
    val niceHours = List.range(0, 6) ++ List.range(10, 14) ++ List.range(20, 23)
    niceHours.map(hour => assert(predictor.canRoad("PBA-123", "2017-03-7", new DateTime(2017, 7, 3, hour, 25))))
  }

  test("catch bad plate CustomException") {
    val thrown = intercept[CustomException] {
      predictor.canRoad("ABC", "2017-03-7", new DateTime(2017, 7, 3, 2, 25))
    }
    assert(thrown.msg === "bad plate string")
  }

  test("catch bad date string format") {
    val thrown = intercept[CustomException] {
      predictor.canRoad("ABC-123", "2017-A-B", new DateTime(2017, 7, 3, 2, 25))
    }
    assert(thrown.msg === "bad date string")
  }

  test("test the bad day for every plate number") {
    assert(!predictor.canRoad("PBA-121", "2017-03-6", new DateTime(2017, 7, 6, 8, 25)))
    assert(!predictor.canRoad("PBA-122", "2017-03-6", new DateTime(2017, 7, 6, 8, 25)))
    assert(!predictor.canRoad("PBA-123", "2017-03-7", new DateTime(2017, 7, 6, 8, 25)))
    assert(!predictor.canRoad("PBA-124", "2017-03-7", new DateTime(2017, 7, 6, 8, 25)))
    assert(!predictor.canRoad("PBA-125", "2017-03-8", new DateTime(2017, 7, 6, 8, 25)))
    assert(!predictor.canRoad("PBA-126", "2017-03-8", new DateTime(2017, 7, 6, 8, 25)))
    assert(!predictor.canRoad("PBA-127", "2017-03-9", new DateTime(2017, 7, 6, 8, 25)))
    assert(!predictor.canRoad("PBA-128", "2017-03-9", new DateTime(2017, 7, 6, 8, 25)))
    assert(!predictor.canRoad("PBA-129", "2017-03-10", new DateTime(2017, 7, 6, 8, 25)))
    assert(!predictor.canRoad("PBA-120", "2017-03-10", new DateTime(2017, 7, 6, 8, 25)))
  }

}