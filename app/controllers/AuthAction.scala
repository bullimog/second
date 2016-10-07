package controllers

import play.api.mvc._
import scala.concurrent.Future




object AuthAction extends AuthAction{
  def apply(action: AsyncPlayRequest): Action[AnyContent] = authenticate(action)
}

trait AuthAction extends Controller{
  type AsyncPlayRequest = (Request[AnyContent] => Future[Result])

  def authenticate(action:AsyncPlayRequest):Action[AnyContent] = Action.async{
    request => {
      if (request.session.get("logged-in-key").isDefined)
        action(request)
      else
        Future.successful(Forbidden("Not Logged In, man!"))
    }
  }
}
