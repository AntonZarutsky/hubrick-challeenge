package de.hubrick.challenge.zarutsky.model;

import de.hubrick.challenge.zarutsky.domain.Gender;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import static de.hubrick.challenge.zarutsky.domain.Gender.FEMALE;
import static de.hubrick.challenge.zarutsky.domain.Gender.MALE;
import static java.util.Optional.empty;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


@RunWith(Enclosed.class)
public class GenderTest {

    @RunWith(Parameterized.class)
    public static class TestProperValues {

        @Parameter
        public String shortName;

        @Parameter(value = 1)
        public Gender gender;

        @Parameters
        public static Collection parameters() {
            return Arrays.asList(new Object[][] {
                    { "f", FEMALE },
                    { "m", MALE },
            });
        }

        @Test
        public void test_correct_short_gender_names() throws Exception {
            assertThat(Gender.byShortName(shortName).get(), equalTo(gender));
        }
    }

    @RunWith(Parameterized.class)
    public static class TestWrongValues {

        @Parameter
        public String shortName;

        @Parameterized.Parameters
        public static Collection parameters() {
            return Arrays.asList(new Object[][] {
                    { "a" },
                    { "F" },
                    { "" },
                    {"FEMALE"},
                    {null},
            });
        }

        @Test
        public void test_correct_short_gender_names() throws Exception {
            assertThat(Gender.byShortName(shortName), equalTo(empty()));
        }
    }

}

