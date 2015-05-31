package controllers



/**
 * Created by graeme on 31/05/15.
 */
class Json {
  import play.api.libs.json._

  // Different ways of defining a Writes:

  //Very simple Writes. Often impractical...
  implicit val devWrites = Json.writes[Device]

  //  //Define custom mapping... Not needed in this instance
  //  implicit val deviceWrites = new Writes[Device] {
  //    def writes(device: Device) = Json.obj(
  //      "id"          -> device.id,
  //      "description" -> device.description,
  //      "deviceType"  -> device.deviceType,
  //      "port"        -> device.port
  //    )
  //  }


  // The above method wouldn't handle a Seq, so used this combinator pattern, instead...
  // But as it turned out, the default, simple Writes did the job!
  //  implicit val devicesWrites: Writes[DeviceCollection] = (
  //      (JsPath \ "name").write[String] and
  //      (JsPath \ "description").write[String] and
  //      (JsPath \ "devices").write[Seq[Device]]
  //    )(unlift(DeviceCollection.unapply))

  implicit val devicesWrites = Json.writes[DeviceCollection]

  val isThisIt = Json.toJson(devices)
  println("---------> toJson" + isThisIt)



}
