package services

/**
  * Created by dcarroll on 4/27/17.
  */
object Organization {
  def apply(orgname: String): Organization = {
    Organization(orgname, orgname, orgname)
  }
}

case class Organization(id: String, name: String, slug: String)
