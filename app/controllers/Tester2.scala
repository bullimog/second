package controllers

import models.UrlModel
import play.api.mvc._
import scala.concurrent.Future
import play.api.libs.ws.{WS, WSRequestHolder, WSResponse}
import forms.UrlForms.urlForm


//had to add these two in, to get WS stuff to work
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.Play.current

object Tester2 extends Controller {

  def display = Action {
    Ok(views.html.tester2(urlForm, "first display"))
  }


  def submit() = Action.async { implicit request =>
    urlForm.bindFromRequest.fold(
      errors => Future.successful(Ok(views.html.index(errors, "Some form errors"))),
      value => handleSomething(value))
  }


  private def handleSomething(url: UrlModel) : Future[Result] = {
    doTwoThings("foo").map {
      aString => Ok(views.html.tester2(urlForm.fill(url), aString))
    }
  }


  def doTwoThings(aString: String): Future[String] = {
    for {
      first <- doSomethingBig("first")
      second <- doSomethingBig("second")
    } yield {
      s"Used for comprehension to get person: $first aged $second"
    }
  }

  def doThreeThings(aString: String): Future[List[String]] = {
    val listOfFutures: List[Future[String]] = List(doSomethingBig("first"), doSomethingBig("second"), doSomethingBig("third"))
    Future.sequence(listOfFutures)
  }

  def doSomethingBig(aString: String): Future[String] = {
    println("Started to do something big: " + aString)
    Future {
      Thread.sleep(2000)
      println("Finished doing something big: " + aString)
      aString + " = = = = " + aString
    }
  }
}