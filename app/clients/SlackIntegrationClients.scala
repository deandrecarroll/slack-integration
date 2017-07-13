package clients

import play.api.Logger
import play.api.libs.ws.WS
import play.api.Play.current
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by dcarroll on 4/21/17.
  */
object SlackIntegrationClients {
  def commandResponse(responseUrl: String, body: String) = {
    WS.url(responseUrl).post(body).map { response => Logger.info(response.body.take(100)) }
  }
}
