package example.micronaut

import io.micronaut.context.ApplicationContext
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.RxHttpClient
import io.micronaut.security.authentication.UsernamePasswordCredentials
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken
import org.junit.Assume
import spock.lang.AutoCleanup
import spock.lang.IgnoreIf
import spock.lang.Shared
import spock.lang.Specification

@IgnoreIf({ System.getenv("TRAVIS") })
class AcceptanceSpec extends Specification implements MicroserviceHealth {

    @Shared
    @AutoCleanup
    ApplicationContext applicationContext = ApplicationContext.run()

    @Shared
    @AutoCleanup
    RxHttpClient client = applicationContext.createBean(RxHttpClient, 'http://localhost:8080')

    def "verifies three microservices collaborate together with JWT authentication"() {

        given:
        Assume.assumeTrue(isUp('http://localhost:8080'))
        Assume.assumeTrue(isUp('http://localhost:8081'))

        when:
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("sherlock", "password")
        HttpRequest request = HttpRequest.POST("/login", credentials)

        HttpResponse<BearerAccessRefreshToken> rsp = client.toBlocking().exchange(request, BearerAccessRefreshToken)
        then:
        rsp.status() == HttpStatus.OK
        rsp.body().accessToken
        rsp.body().refreshToken
        rsp.body().username

        String username = client.toBlocking().retrieve(HttpRequest.GET("/user")
                .header("Authorization", "Bearer ${rsp.body().accessToken}".toString()), String.class)

        then:
        noExceptionThrown()
        username == 'sherlock'
    }
}
