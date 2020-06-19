package example.micronaut

import geb.spock.GebSpec
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.BlockingHttpClient
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.exceptions.HttpClientException
import spock.lang.Requires

class AcceptanceSpec extends GebSpec {

    static final String BOOKS_URL = 'http://localhost:8081'
    static final String GATEWAY_URL = 'http://localhost:8080'

    @Requires( {
        Closure isUp = { client, url ->
            String microservicesUrl = url.endsWith('/health') ? url : "${url}/health"
            try {
                StatusResponse statusResponse = client.retrieve(HttpRequest.GET(microservicesUrl), StatusResponse)
                if ( statusResponse.status == 'UP' ) {
                    return true
                }
            } catch (HttpClientException e) {
                println "HTTP Client exception for $microservicesUrl $e.message"
            }
            return false
        }
        BlockingHttpClient booksClient = HttpClient.create(new URL(BOOKS_URL)).toBlocking()
        BlockingHttpClient analyticsClient = HttpClient.create(new URL(GATEWAY_URL)).toBlocking()
        return isUp(booksClient, BOOKS_URL) && isUp(analyticsClient, GATEWAY_URL)
    })
    def "verifies tenant id is propagated"() {

        when:
        browser.baseUrl = GATEWAY_URL
        browser.go("/")

        then:
        at TenantPage

        when:
        TenantPage page = browser.page(TenantPage)
        page.select("sherlock")

        then:
        at HomePage

        when:
        HomePage homePage = browser.page(HomePage)

        then:
        homePage.numberOfBooks()
        homePage.books() == ['Sherlock diary']

        when:
        to TenantPage
        page.select("watson")
        then:
        at HomePage

        when:
        homePage = browser.page(HomePage)

        then:
        homePage.numberOfBooks()
        homePage.books() == ['Watson diary']

    }
}
