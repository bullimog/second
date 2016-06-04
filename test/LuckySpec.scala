import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import utils.Lucky


@RunWith(classOf[JUnitRunner])
 class LuckySpec extends Specification {

  "Lucky" should {

    "extract the correct digit from an Integer " in {
      val result = Lucky.extractDigit(1234, 2)
      result must equalTo(2)
    }

    "extract the correct digit from an Integer " in {
      val result = Lucky.extractDigit(7654321, 2)
      result must equalTo(3)
    }

    "check those units and tens" in {
      val units = Lucky.units(345)
      units must equalTo(5)

      val tens = Lucky.tens(345)
      tens must equalTo(4)

      val hundreds = Lucky.hundreds(345)
      hundreds must equalTo(3)
    }


    "again extract the correct digit from an Integer " in {
      val result = Lucky.colVal(632423478, 5)
      result must equalTo(2)
    }

    "count number of digits" in {
      val result = Lucky.numOfDigits(111181)
      result must equalTo(6)
    }


    "zip up a number" in {
      val result = Lucky.zipup(111711)
      result must equalTo(12)
    }


    "fully zip up a number" in {
      val result = Lucky.zipToSingleDigit(5117118)
      result must equalTo(6)
    }

  }
}
