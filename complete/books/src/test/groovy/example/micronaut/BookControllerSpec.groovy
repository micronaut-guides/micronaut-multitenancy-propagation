package example.micronaut

import io.micronaut.context.ApplicationContext
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.RxHttpClient
import io.micronaut.runtime.server.EmbeddedServer
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

class BookControllerSpec extends Specification {

    @Shared
    @AutoCleanup
    EmbeddedServer embeddedServer = ApplicationContext.run(EmbeddedServer)

    @Shared
    @AutoCleanup
    RxHttpClient client = embeddedServer.applicationContext.createBean(RxHttpClient, embeddedServer.getURL())

    void "test hello world response"() {
        when:
        HttpRequest request = HttpRequest.GET('/books').header("tenantId", "sherlock")
        List<String> rsp  = client.toBlocking().retrieve(request, Argument.of(List, String))

        then:
        rsp
        rsp == ['Sherlock diary']

        when:
        request = HttpRequest.GET('/books').header("tenantId", "watson")
        rsp  = client.toBlocking().retrieve(request, Argument.of(List, String))

        then:
        rsp
        rsp == ['Watson diary']
    }


}
