package example.micronaut

import io.micronaut.context.annotation.Requires
import io.micronaut.context.env.Environment
import io.reactivex.Single

import javax.inject.Singleton

@Singleton
@Requires(env = Environment.TEST)
class MockBookFetcher implements BookFetcher {

    @Override
    Single<List<String>> fetchBooks() {
        Single.just(Arrays.asList("The Empty Hearse",
                "The Hounds of Baskerville",
                "The Woman",
                "The Six Thatchers",
                "The Aluminium Crutch",
                "The Speckled Blonde",
                "The Geek Interpreter",
                "The Great Game",
                "The Blind Banker",
                "A Study in Pink"))
    }
}
