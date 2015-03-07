package controllers

import models.UrlModel
import play.api.libs.json.JsValue
import play.api.mvc._
import play.mvc.Http.Response
import scala.concurrent.{Future, ExecutionContext}
import play.api.libs.ws.{WS, WSRequestHolder, WSResponse}
import forms.UrlForms.urlForm

//had to add these two in, to get WS stuff to work
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.Play.current

object Application extends Controller {

  def display = Action {
    Ok(views.html.index(urlForm, "first display"))
  }


  def simpleSubmit() = Action { implicit request =>
    urlForm.bindFromRequest.fold(
      formWithErrors => Forbidden("Oh noes, invalid submission!"),
      value => Ok(views.html.index(urlForm.fill(value), "simple controller used")))
  }


  def submit() = Action.async { implicit request =>
    urlForm.bindFromRequest.fold(
      errors => Future.successful(Ok(views.html.index(errors, "Some form errors"))),
      value => handleSomething(value))
  }


  private def handleSomething(url: UrlModel) : Future[Result] = {
    doGet(url.url) match {
      case Some(theFuture) => theFuture.map { wsresponse =>           // get the WSResponse out of the Future using map
        wsresponse.status match {                                     // match on the response status code (int)
          case 200 => Ok(views.html.index(urlForm.fill(url), wsresponse.body))
          case 404 => NotFound(views.html.index(urlForm.fill(url), "The page gives a 404"))
          case _ => InternalServerError
        }
      }
      case None => Future.successful(NotFound(views.html.index(urlForm.fill(url), "The page doesn't exist!!")))
    }
  }

  def doGet(url: String): Option[Future[WSResponse]] = {
    val holder : WSRequestHolder = WS.url(url)
    try{Some(holder.get())}
    catch {
      case _: java.lang.NullPointerException => None
    }
  }


  def doSomethingBig(aString: String): String = {
    Thread.sleep(2000)
    aString + " = = = = " + aString
  }

  private def handleSomethingOlder(url: UrlModel) : Future[Result] = {
    doSomethingBig(url.url) match {
      case "23 = = = = 23" => Future.successful(Ok(views.html.index(urlForm.fill(url), "23 message")))
      case _ => Future.successful(Ok(views.html.index(urlForm.fill(url), "anything else")))
    }
  }




 /*
  def display1 = Action.async {
      val futureint = Future { doSomethingBig(77) }
      futureint.map(i => Ok("async" + i))
  }



  def display2 = Action.async {
    val wsResponse = hitRouter("dave")
    wsResponse.map(r => Ok("async" + r))
  }


  def hitRouter(data: String): Future[WSResponse] = {
    val holder : WSRequestHolder = WS.url("http://192.168.1.254/landing.lp").withQueryString("rn" -> "admin", "hidepw" -> "CP1403VFWWR")
    holder.get()
  }
*/

//  def hitRouter(data: String)(implicit request: Request[_]): Future[WSResponse] = {
//    val holder : WSRequestHolder = WS.url("http://192.168.1.254/landing.lp").withQueryString("rn" -> "admin", "hidepw" -> "CP1403VFWWR")
//    val complexHolder : WSRequestHolder = holder
//    val futureResponse : Future[WSResponse] = complexHolder.get()
//    futureResponse
//  }

//  def hitRouter(data:String) = {
   // val result = Http("http://192.168.1.254/landing.lp").param("rn", "admin").param("hidepw", "CP1403VFWWR").asString

//    val holder : WSRequestHolder = WS.url("http://192.168.1.254/landing.lp").withQueryString("rn" -> "admin", "hidepw" -> "CP1403VFWWR")
//    val complexHolder : WSRequestHolder = holder//.withHeaders(...)
//    val futureResponse : Future[WSResponse] = complexHolder.get()
    //.withTimeout(...)
    //.withQueryString(...)

    //println("result: " + result)
    //result
//  }
/*


*/



}