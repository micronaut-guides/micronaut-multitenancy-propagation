package example.micronaut;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.views.View;
import io.reactivex.Single;

import java.util.HashMap;
import java.util.Map;

@Controller("/") // <1>
public class HomeController {

    private final BookFetcher bookFetcher;

    public HomeController(BookFetcher bookFetcher) { // <2>
        this.bookFetcher = bookFetcher;
    }

    @View("home") // <3>
    @Get // <4>
    Single<HttpResponse<Map<String, Object>>> index() { // <5>
        return bookFetcher.fetchBooks().map(books -> {
            Map<String, Object> model = new HashMap<>();
            model.put("pagetitle", "Home");
            model.put("books", books);
            return HttpResponse.ok(model);
        });
    }

}
