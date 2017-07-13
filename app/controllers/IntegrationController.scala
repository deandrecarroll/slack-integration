package controllers

import play.api.libs.json._
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global
import services.Integration._
import services.{Organization, User}

import scala.concurrent.Future

/**
  * Created by dcarroll on 5/1/17.
  */
class IntegrationController extends Controller
  with IntegrationApi

trait IntegrationApi {
  self: Controller =>

  /**
    * Resolve completed OAuth connection process
    */
  def link(token: String) = Action.async { implicit request =>
    val context = getContext(request)

    linkUser(token, context.user, context.organization) map { iu =>
      Ok(Json.prettyPrint {
        Json.obj("status" -> "OK", "result" -> "Connected", "query" -> request.queryString.toString())
      })
    }
  }

  case class Context(user: User, organization: Organization)

  private def getContext(req: Request[_]) = {
    val headers = req.headers.toMap
    val username = headers("X-info-username")(0)
    val orgname = headers("X-info-orgname")(0)

    Context(User(username), Organization(orgname))
  }

}
