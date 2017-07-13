package messages

import play.api.libs.json.Json

/**
  * Created by dcarroll on 4/21/17.
  */
object SlackIntegrationMessages {

  case class SlackMessage(inChannel: Boolean = true, text: String, attachments: Seq[Attachment])

  case class Attachment()

  case class Verification(token: String, `type`: String, challenge: String)
  implicit val verificationRead = Json.reads[Verification]

  case class EventItem(`type`: String, channel: String)
  implicit val itemRead = Json.reads[EventItem]

  case class Event(`type`: String,  event_ts: String,  user: String,  ts: String,  item: EventItem)
  implicit val eventRead = Json.reads[Event]

  case class EventCallback(token: String, team_id: String, api_app_id: String, event: Event, `type`: String, authed_users: Seq[String], event_id: String, event_time: Int)
  implicit val eventCallbackRead = Json.reads[EventCallback]

  class Command {

  }

}
