package services

/**
  * Created by dcarroll on 4/27/17.
  */
object User {
  def apply(username: String): User = {
    User(username, username)
  }

  def getUserContext = {
    ???
  }

}

case class User(id: String, slug: String)
