package forms

import models.UrlModel
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formatter
import play.api.data.FormError
import play.api.i18n.Messages

object UrlForms {

//create a play Form, to map an html form to a case class (a model)
// default mapping types can be any of:
// boolean, checked, date, email, ignored, list, longNumber, nonEmptyText,
// number, optional, seq, single, sqlDate, or text

  val urlForm = Form(
    mapping(
      "url" -> nonEmptyText,
      "protocol" -> text,
      "overrideProtocol" -> of(validateCheckboxAgainstText("protocol","error.protocol.not.populated"))
    )(UrlModel.apply)(UrlModel.unapply)
  )



  //This method is quite generic, so could be moved into a separate validation object
  def validateCheckboxAgainstText(inputBox:String, error:String) = new Formatter[Boolean] {

    override def bind(key: String, data: Map[String, String]): Either[Seq[FormError], Boolean]= {
        val checkBox = data.getOrElse(key, "")
        val inputBoxValue = data.getOrElse(inputBox, "")

        //returning an Either[Seq[FormError], Boolean] (by convention: left=bad, right=good)
        checkBox match {
          case "true" if inputBoxValue == "" => Left(List(FormError(key, Messages(error))))
          case "" => Right(false)              // if its empty, assume its false
          case _  => Right(checkBox.toBoolean) // otherwise bind boolean value of the checkbox
        }
    }

    override def unbind(key: String, value: Boolean): Map[String, String] = {
      Map(key -> value.toString)
    }
  }
}
