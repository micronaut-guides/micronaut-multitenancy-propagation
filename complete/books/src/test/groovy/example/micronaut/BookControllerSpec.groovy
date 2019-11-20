package example.micronaut

import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest
import spock.lang.Specification

import javax.inject.Inject

@MicronautTest
class BookControllerSpec extends Specification {

    @Inject
    @Client("/")
    RxHttpClient client;

    void "test hello world response"() {
        when:
        HttpRequest request = HttpRequest.GET('/books').header("tenantId", "sherlock")
        List<String> rsp  = client.toBlocking().retrieve(request, Argument.listOf(String))

        then:
        rsp
        rsp == ['Sherlock diary']

        when:
        request = HttpRequest.GET('/books').header("tenantId", "watson")
        rsp  = client.toBlocking().retrieve(request, Argument.listOf(String))

        then:
        rsp
        rsp == ['Watson diary']
    }
}
