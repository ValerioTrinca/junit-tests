package main;

/**
 * Standart methods junit 5 - assertions
 * import org.junit.jupiter.api.Assertions;
 * import static org.junit.jupiter.api.Assertions.assertTrue;
 */


import net.javacrumbs.jsonunit.assertj.JsonAssertions;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;


public class UserTest {

    private User user;

    @BeforeAll
    public static void beforeAll() {
        System.out.println("Before all static method is called before all the tests");
    }

    @AfterAll
    public static void afterAll() {
        System.out.println("After all static method is called after all the tests");
    }

    @BeforeEach
    public void setup() {
        this.user = new User("Valerio", 24, false, LocalDate.now().minusYears(24));
        System.out.println("Setup complete");
    }

     @AfterEach
     public void cleanup() {
        this.user = null;
        System.out.println("Cleanup was called");
     }

    @Test
    @DisplayName("User should be al least 18")
    public void userShouldBeAtLeast18() {

        /**
         * Standard methods junit 5 - assertions
         * Assertions.assertTrue(user.age() >= 18);
         * assertTrue(user.age() >= 18);
         */

        Assertions.assertThat(user.age())
                .as("User %s age should be greated than 18", user.name())
                .isGreaterThanOrEqualTo(18);

    }

    @Test
    @DisplayName("User's name should be starts with Val")
    public void userShouldBeValerio() {
        Assertions.assertThat(user.name())
                .as("User's name should starts with Val")
                .startsWith("Val");
    }

    @Test
    @DisplayName("User should not blocked")
    public void userShouldBeNotBlocked() {
        Assertions.assertThat(user.blocked())
                .as("User %s should not blocked", user.name())
                .isFalse();
    }

    @Test
    @DisplayName("Json user test")
    public void jsonUserTest() {
        JsonAssertions.assertThatJson(user)
                .as("Json user test")
                .isEqualTo("{\"name\":\"Valerio\",\"age\":24,\"blocked\":false,\"birthDate\":[1998,5,12]}");
    }

    @ParameterizedTest
    @ValueSource(ints = {20, 50, 80})
    @DisplayName("All friends should be at least 18")
    public void allFriendsShouldBeAtLeast18(int ages) {
        Assertions.assertThat(ages)
                .as("All friends should be at least 18")
                .isGreaterThanOrEqualTo(18);
    }
}
