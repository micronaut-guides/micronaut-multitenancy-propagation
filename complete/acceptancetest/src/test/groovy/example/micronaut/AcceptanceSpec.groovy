package example.micronaut

import geb.spock.GebSpec
import io.micronaut.context.ApplicationContext
import io.micronaut.http.client.RxHttpClient
import org.junit.Assume
import spock.lang.AutoCleanup
import spock.lang.IgnoreIf
import spock.lang.Shared

class AcceptanceSpec extends GebSpec implements MicroserviceHealth {

    @Shared
    @AutoCleanup
    ApplicationContext applicationContext = ApplicationContext.run()

    @Shared
    @AutoCleanup
    RxHttpClient client = applicationContext.createBean(RxHttpClient, 'http://localhost:8080')

    @IgnoreIf({ !(sys['geb.env'] in ['chrome', 'firefox']) })
    def "verifies three microservices collaborate together with JWT authentication"() {

        given:
        Assume.assumeTrue(isUp('http://localhost:8080'))
        Assume.assumeTrue(isUp('http://localhost:8081'))

        when:
        browser.baseUrl = 'http://localhost:8080'
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
