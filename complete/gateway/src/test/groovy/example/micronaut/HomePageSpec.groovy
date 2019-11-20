package example.micronaut

import geb.spock.GebSpec
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.annotation.MicronautTest
import spock.lang.IgnoreIf

import javax.inject.Inject

@MicronautTest // <1>
class HomePageSpec extends GebSpec {

    @Inject
    EmbeddedServer embeddedServer // <2>

    @IgnoreIf({ !(sys['geb.env'] in ['chrome', 'firefox']) })
    def "verify tenant can be selected works"() {
        given:
        browser.baseUrl = "http://localhost:${embeddedServer.port}"

        when:
        go "/"

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
    }
}
