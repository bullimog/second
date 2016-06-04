package controllers

import play.api.mvc._
import scala.concurrent.Future


trait MySimpleAuthenticatedController extends Controller{

  type AsyncPlayRequest = (Request[AnyContent] => Future[Result])

  def AuthenticatedAction(action: AsyncPlayRequest) = Action.async { implicit request =>
    request.session.get("logged-in-key") match {
      case Some(value) => action(request)
      case None => Future.successful(Ok("Not Logged in!"))
    }
  }
}
