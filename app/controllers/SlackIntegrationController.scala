package controllers

import play.api.libs.json._
import play.api.mvc._
import messages.SlackIntegrationMessages._
import clients.SlackIntegrationClients._
import services._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Success

/**
  * Created by dcarroll on 4/21/17.
  */

class SlackIntegrationController extends Controller
  with SlackIntegrationApi

trait SlackIntegrationApi {
  self: Controller =>

  /**
    * Resolve completed OAuth connection process
    */
  def connect = Action.async { implicit request =>
    Future {
      Ok(Json.prettyPrint {
        Json.obj("status" -> "OK", "result" -> "Connected", "query" -> request.queryString.toString())
      })
    }
  }

  /**
    * Endpoint for Slack slash commands
    */
  def commands = Action.async(parse.urlFormEncoded) { implicit request =>
    Future {
      Ok(Json.prettyPrint {
        Json.obj("status" -> "OK", "result" -> "Command received", "command values" -> request.queryString.toString())
      })
    } andThen {
      case Success(_) => commandResponse("http://requestb.in/11zucde1", request.body.toString())
    }
  }

  /**
    * Endpoint for Slack events
    */
  def events = Action.async(parse.json) { implicit request =>
    Future {
      request.body.validate[Verification] orElse request.body.validate[EventCallback] match {
        case JsSuccess(message, _) => {
          val response = message match {
            case msg: Verification => Json.obj("challenge" -> JsString(msg.challenge))
            case msg: EventCallback => request.body
          }
          Ok(Json.prettyPrint(response))
        }

        case _ => BadRequest
      }
    }
  }

  /**
    * Endpoint for Slack messages
    */
  def messages = Action.async(parse.json) { implicit request =>
    Future {
      Ok(Json.prettyPrint {
        Json.obj("resource" -> "Messages", "result" -> "Interactive message object")
      })
    }
  }

  /**
    * Endpoint for Slack message options
    */
  def messageOptions = Action.async(parse.json) { implicit request =>
    Future {
      Ok(Json.prettyPrint {
        Json.obj("resource" -> "Message Options", "result" -> "Interactive message options object")
      })
    }
  }

}
