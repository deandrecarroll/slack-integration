package services

import java.util.UUID
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

/**
  * Created by dcarroll on 4/27/17.
  */
object Integration {

  def getLinkToken(integrationId: String, orgId: Int, integrationUserId: String): Future[String] = {
    saveUser(IntegrationUser(integrationUserId, None, UUID.randomUUID().toString, integrationId, orgId)).map(_.token)
  }

  def linkUser(token: String, user: User, organization: Organization): Future[IntegrationUser] = {
    getUser(token) flatMap { iu =>
      if (iu.voSlug.isEmpty)
        updateUser(IntegrationUser(iu.id, Some(user.slug), iu.token, iu.integrationId, iu.orgId))
      else
        Future(iu)
    }
  }

  def saveUser(user: IntegrationUser): Future[IntegrationUser] = {
    ???
  }

  def updateUser(user: IntegrationUser): Future[IntegrationUser] = {
    ???
  }

  def getUser(token: String): Future[IntegrationUser] = {
    ???
  }

}

case class Integration(id: String, name: String)
case class IntegrationUser(id: String, voSlug: Option[String], token: String, integrationId: String, orgId: Int)
