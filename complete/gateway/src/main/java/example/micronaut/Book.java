package example.micronaut;

import io.micronaut.core.annotation.Introspected;

@Introspected
public class Book {
    private String title;

    public Book() {
    }

    public Book(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
