import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class UploadSimulation extends Simulation {
    val httpProtocol = http
        // Here is the root for all relative URLs
        .baseUrl("http://127.0.0.1:8080")
        // Here are the common headers
        .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
        .doNotTrackHeader("1")
        .acceptLanguageHeader("zh-CN,zh;q=0.5")
        .acceptEncodingHeader("gzip, deflate")
        .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")

    val testFile = "C:/Space/Workspace/testdata/cat.mp4"

    val request = http("upload").put("/video").bodyPart(RawFileBodyPart("file", testFile))

    // A scenario is a chain of requests and pauses
    val scn = scenario("upload").exec(request)
    setUp(scn.inject(constantUsersPerSec(0.5).during(32.seconds)).protocols(httpProtocol))
}
