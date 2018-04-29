import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class BasicSimulation extends Simulation {

  val httpConf = http
    .baseURL("http://localhost:8080/transactions")
    .acceptHeader("application/json")
    .doNotTrackHeader("1")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")

  val headers_10 = Map("Content-Type" -> "application/json")

  val scn = scenario("Scenario Name")
    .exec(http("get summary")
      .get("/"))
    .exec(http("post transaction")
      .post("/")
      .headers(headers_10)
      .body(StringBody("""{"amount": 10.0}""")))

  setUp(scn.inject(constantUsersPerSec(8) during(10 minutes)).protocols(httpConf))
}
