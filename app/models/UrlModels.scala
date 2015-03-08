package models

import org.joda.time.DateTime
import play.api.libs.json.Json


case class UrlModel(url:String, protocol:String, overrideProtocol:Boolean)

object UrlModel {
  implicit val formats=Json.format[UrlModel]
}


