package example.micronaut;

import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.client.annotation.Client;
import io.reactivex.Single;

@Client(id = "userecho") // <1>
@Requires(notEnv = Environment.TEST) // <2>
public interface UserEchoClient extends UsernameFetcher {

    @Override
    @Get("/user") // <3>
    Single<String> findUsername(@Header("Authorization") String authorization); // <4>
}
