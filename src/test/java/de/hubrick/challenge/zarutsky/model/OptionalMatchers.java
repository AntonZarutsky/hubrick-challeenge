package de.hubrick.challenge.zarutsky.model;


import java.util.Optional;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class OptionalMatchers {

    public static Matcher isPresent() {
        return new TypeSafeMatcher<Optional>() {
            @Override
            protected boolean matchesSafely(Optional item) {
                return item != null && item.isPresent();
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("expected expected from isPresent(): ")
                            .appendValue(true);
            }
        };
    }

}
