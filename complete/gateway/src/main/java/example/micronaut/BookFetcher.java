package example.micronaut;

import io.reactivex.Single;

import java.util.List;

public interface BookFetcher {

    Single<List<String>> fetchBooks();
}
