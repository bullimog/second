package controllers

import scala.concurrent.Future


object SimpleProtectedApplication extends MySimpleAuthenticatedController {

  def authed = AuthenticatedAction{
    //body of AuthenticatedAction conforms to AsyncPlayRequest: Request[AnyContent] => Future[Result]
    implicit request =>
    Future.successful(Ok("You are logged into a very simple page"))
  }

}
