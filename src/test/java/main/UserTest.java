package main;

// Standard methods junit 5 - assertions
// import org.junit.jupiter.api.Assertions;
// import static org.junit.jupiter.api.Assertions.assertTrue;

import net.javacrumbs.jsonunit.assertj.JsonAssertions;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.xmlunit.assertj.XmlAssert;
import util.Resources;
import util.Xml;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Tag is useful for assign a tag to every test (class or method) during the CI/CD pipeline (Jenkins, TeamCity, ecc)
 */
@Tag("integration")
public class UserTest {

    static private final int minAge = 18;

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
                .isGreaterThanOrEqualTo(minAge);

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
                .isGreaterThanOrEqualTo(minAge);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/friends.csv", numLinesToSkip = 1)
    @DisplayName("Friends.csv test")
    public void csvFriendsFile(String name, int age) {
        Assertions.assertThat(age)
                .as("Friend %s should be at least %i", name, minAge)
                .isGreaterThanOrEqualTo(minAge);
    }

    @TestFactory
    Collection<DynamicTest> dynamicTestsCreatedThroughCode() {
        List<Xml> xmls = Resources.toStrings("users.*\\.xml");
        return xmls.stream().map(xml -> DynamicTest.dynamicTest(xml.name(), () -> {
            XmlAssert.assertThat(xml.content()).hasXPath("/users/user/name");
        })).collect(Collectors.toList());
    }
}
