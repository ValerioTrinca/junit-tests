package main;

/**
 * Standart methods junit 5 - assertions
 * import org.junit.jupiter.api.Assertions;
 * import static org.junit.jupiter.api.Assertions.assertTrue;
 */


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;


public class UserTest {

    User user = new User("Valerio", 24, false, LocalDate.now().minusYears(24));

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
}
