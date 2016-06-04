package controllers
import models.UrlModel
import models.UrlModel
import play.api.libs.json.JsValue
import play.api.mvc._
import play.mvc.Http.Response
import scala.concurrent.{Await, Future, ExecutionContext}
import play.api.libs.ws.{WS, WSRequestHolder, WSResponse}
import forms.UrlForms.urlForm
import scala.concurrent.duration._

//had to add these two in, to get WS stuff to work
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.Play.current

object Application extends Controller {

  def display = Action {
    Ok(views.html.index(urlForm, "first display"))
  }

  //not used
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


  def doThreeThings(url: UrlModel): Future[List[String]] = {
    val listOfFutures: List[Future[String]] = List(doSomethingBig("first"), doSomethingBig("second"), doSomethingBig("third"))
    Future.sequence(listOfFutures)
  }


  def doTwoThings(url: UrlModel): Future[String] = {
    for {
      first <- doSomethingBig("first")
      second <- doSomethingBig("second")
    } yield {
      s"Used for comprehension to get person: $first aged $second"
    }
  }


  def doSomethingBig(aString: String): Future[String] = {
    Future {
      println("Started to do something big: " + aString)
      Thread.sleep(2000)
      println("Finsihed doing something big: " + aString)
      aString + " = = = = " + aString
    }
  }

/*
  private def handleSomethingOlder(url: UrlModel) : Future[Result] = {
    doSomethingBig(url.url) match {
      case "23 = = = = 23" => Future.successful(Ok(views.html.index(urlForm.fill(url), "23 message")))
      case _ => Future.successful(Ok(views.html.index(urlForm.fill(url), "anything else")))
    }
  }
*/



 /*
  def hitRouter(data: String): Future[WSResponse] = {
    val holder : WSRequestHolder = WS.url("http://192.168.1.254/landing.lp").withQueryString("rn" -> "admin", "hidepw" -> "noPwd")
    holder.get()
  }
*/

//  def hitRouter(data: String)(implicit request: Request[_]): Future[WSResponse] = {
//    val holder : WSRequestHolder = WS.url("http://192.168.1.254/landing.lp").withQueryString("rn" -> "admin", "hidepw" -> "noPwd")
//    val complexHolder : WSRequestHolder = holder
//    val futureResponse : Future[WSResponse] = complexHolder.get()
//    futureResponse
//  }

//  def hitRouter(data:String) = {
   // val result = Http("http://192.168.1.254/landing.lp").param("rn", "admin").param("hidepw", "CP1403VFWWR").asString

//    val holder : WSRequestHolder = WS.url("http://192.168.1.254/landing.lp").withQueryString("rn" -> "admin", "hidepw" -> "NoPwd")
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