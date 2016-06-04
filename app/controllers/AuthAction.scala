package controllers

import play.api.mvc._
import scala.concurrent.Future

object AuthAction extends AuthAction{
  def apply(action: AsyncPlayRequest): Action[AnyContent] = authenticate(action)
}

trait AuthAction extends Controller{
  type AsyncPlayRequest = (Request[AnyContent] => Future[Result])

  def authenticate(action:AsyncPlayRequest):Action[AnyContent] = Action.async{
    request => request.session.get("logged-in-key") match {
      case Some(value) => action(request)
      case None => Future.successful(Ok("Not Logged in!"))
    }
  }
}
