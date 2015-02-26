package forms

import models.UrlModel
import play.api.data.Form
import play.api.data.Forms._

object UrlForms {


  val urlForm = Form(mapping(
    "url" -> nonEmptyText)(UrlModel.apply)(UrlModel.unapply)
  )

}


//.verifying("Needs to be more than two chars", _.length > 2)