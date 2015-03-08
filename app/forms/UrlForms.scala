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
      "protocol" -> of(validateTextAgainstCheckbox("overrideProtocol","error.protocol.not.populated")),
      "overrideProtocol" -> boolean
    )(UrlModel.apply)(UrlModel.unapply)
  )


  //This method is quite generic, so could be moved into a separate validation object
  def validateTextAgainstCheckbox(checkBoxKey:String, errorMessageKey:String) = new Formatter[String] {

    override def bind(inputBoxKey: String, formDataMap: Map[String, String]): Either[Seq[FormError], String]= {
      val inputBoxValue = formDataMap.getOrElse(inputBoxKey, "")
      val checkBoxValue = formDataMap.getOrElse(checkBoxKey, "")

      //returning an Either[Seq[FormError], String] (by convention: left=bad, right=good)
      inputBoxValue match {
        case "" if checkBoxValue == "true" => Left(List(FormError(inputBoxKey, Messages(errorMessageKey))))
        case _  => Right(inputBoxValue) // otherwise bind the String value
      }
    }

    override def unbind(key: String, value: String): Map[String, String] = {
      Map(key -> value.toString)
    }
  }

}
