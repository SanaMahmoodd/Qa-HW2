package main.najah.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import main.najah.code.UserService;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceSimpleTest {

    private UserService userService;

    @BeforeAll
    static void initAll() {
        System.out.println("Testing has start...");
    }
    
    @BeforeEach
    void setUp() {
        userService = new UserService();
    }

    @Test
    @Order(1)
    @DisplayName("Test isValidEmail with valid email")
    void testIsValidEmailValid() {
        String validEmail = "sana@gmail.com";
        assertTrue(userService.isValidEmail(validEmail), "The email should be valid.");
        assertNotNull(validEmail, "The email should not be null.");
    }

    @Test
    @Order(2)
    @DisplayName("Test isValidEmail with invalid email")
    void testIsValidEmailInvalid() {
        String invalidEmail = "sana21.com";
        assertFalse(userService.isValidEmail(invalidEmail), "The email should be invalid.");
    }

    @Test
    @Order(3)
    @DisplayName("Test authenticate with valid credentials")
    void testAuthenticateValidCredentials() {
        String username = "admin";
        String password = "1234";
        assertTrue(userService.authenticate(username, password), "The credentials should be valid.");
        assertEquals("admin", username, "Username should match the provided username.");
    }

    @Test
    @Order(4)
    @DisplayName("Test authenticate with invalid username")
    void testAuthenticateInvalidUsername() {
        String username = "sana";
        String password = "1233";
        assertFalse(userService.authenticate(username, password), "The username should be invalid.");
    }

    @Test
    @Order(5)
    @DisplayName("Test authenticate with invalid password")
    void testAuthenticateInvalidPassword() {
        String username = "admin";
        String password = "wrongpassword";
        assertFalse(userService.authenticate(username, password), "The password should be invalid.");
    }

    @Test
    @Order(6)
    @DisplayName("Test authenticate with invalid credentials")
    void testAuthenticateInvalidCredentials() {
        String username = "sana21";
        String password = "1020304050";
        assertFalse(userService.authenticate(username, password), "The credentials should be invalid.");
    }

    @ParameterizedTest
    @Order(7)
    @CsvSource({
        "sana21@example.com, true",
        "hii@com, false",
        "s12217400@stu.com, true",
        "sanasaleh.com, false"
    })
    @DisplayName("Test isValidEmail with parameterized inputs")
    void testIsValidEmailParameterized(String email, boolean expected) {
        assertEquals(expected, userService.isValidEmail(email), "The email validation result should match expected value.");
    }

    @Test
    @Order(8)
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    @DisplayName("Test authenticate method with a timeout")
    void testAuthenticateTimeout() {
        String username = "admin";
        String password = "1234";
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            assertTrue(userService.authenticate(username, password), "Authentication should finish within the time limit.");
        });
    }

    @Test
    @Disabled("Fix: Password should not be allowed to be null.")
    @Order(9)
    @DisplayName("Test authenticate with null password (intentionally failing)")
    void testAuthenticateNullPassword() {
        String username = "admin";
        String password = null;
        // This test will fail if null password is passed, and the behavior should be fixed by updating the authenticate method
        assertFalse(userService.authenticate(username, password), "Authentication should fail for null password.");
    }
    
    @AfterEach
    void tearDown() {
        System.out.println("Test completed :)");
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("All tests have been completed!");
    }
    
}