package controllers

import play.api.mvc.Controller

import scala.concurrent.Future


object ProtectedApplication extends Controller {

  def authed = AuthAction{
    //body of AuthAction conforms to AsyncPlayRequest: Request[AnyContent] => Future[Result]
    implicit request =>
    Future.successful(Ok("*** You are logged into a very simple page"))
  }

}
