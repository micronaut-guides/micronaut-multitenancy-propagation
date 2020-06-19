package example.micronaut;

import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;
import io.reactivex.Single;

import java.util.List;

@Client("books") // <1>
@Requires(notEnv = Environment.TEST) // <2>
public interface BookClient extends BookFetcher {

    @Override
    @Get("/books") // <3>
    Single<List<Book>> fetchBooks();
}
