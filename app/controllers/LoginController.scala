package controllers

import play.api.mvc._

import scala.concurrent.Future


object LoginController extends Controller {

  def login = Action.async {
    implicit request =>
    Future.successful(Ok("Logging you in now...").withSession("logged-in-key"->"a-key"))

  }

  def logout = Action{
    implicit request =>
      Ok("Logging you out now...").removingFromSession("logged-in-key")

  }
}
