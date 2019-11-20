package example.micronaut;

import io.micronaut.core.async.publisher.Publishers;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.filter.OncePerRequestHttpServerFilter;
import io.micronaut.http.filter.ServerFilterChain;
import io.micronaut.multitenancy.exceptions.TenantNotFoundException;
import io.micronaut.multitenancy.tenantresolver.TenantResolver;
import org.reactivestreams.Publisher;

import java.net.URI;

@Filter("/") // <1>
public class HomePageFilter extends OncePerRequestHttpServerFilter {

    private final TenantResolver tenantResolver;

    public HomePageFilter(TenantResolver tenantResolver) { // <2>
        this.tenantResolver = tenantResolver;
    }

    @Override
    protected Publisher<MutableHttpResponse<?>> doFilterOnce(HttpRequest<?> request, ServerFilterChain chain) {
        try {
            tenantResolver.resolveTenantIdentifier();
        } catch (TenantNotFoundException e) {
            return Publishers.just(HttpResponse.seeOther(URI.create("/tenant")));
        }
        return chain.proceed(request);
    }
}
