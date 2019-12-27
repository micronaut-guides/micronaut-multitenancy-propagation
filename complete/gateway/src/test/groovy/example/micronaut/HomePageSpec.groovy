package example.micronaut

import geb.spock.GebSpec
import io.micronaut.context.ApplicationContext
import io.micronaut.runtime.server.EmbeddedServer
import spock.lang.AutoCleanup
import spock.lang.Shared

class HomePageSpec extends GebSpec {

    @AutoCleanup
    @Shared
    EmbeddedServer embeddedServer = ApplicationContext.run(EmbeddedServer, [:])

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
