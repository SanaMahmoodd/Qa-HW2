package main.najah.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import main.najah.code.Calculator;


@DisplayName("Calculator Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Execution(ExecutionMode.CONCURRENT) 
public class CalculatorTest {
    
    Calculator calc;

    @BeforeEach
    void setUp() {
        calc = new Calculator(); // Create a Calculator object before each test
        System.out.println(Thread.currentThread().getName() + " started test.");
    }

    @Test
    @Order(1)
    @DisplayName("Addition Test")
    void testAddition() {
        assertEquals(5, calc.add(2, 3), "2 + 3 should be 5");
        assertEquals(15, calc.add(1, 2, 3, 4, 5), "1 + 2 + 3 + 4 + 5 should be 15");
    }
    
    @ParameterizedTest
    @Order(2)
    @ValueSource(ints = {1, 2, 3, 4, 5})
    @DisplayName("Test multiple additions")
    void testMultipleAdditions(int num) {
        assertEquals(num + 2, calc.add(2, num), "2 + " + num + " should be " + (2 + num));
    }

    @Test
    @Order(3)
    @DisplayName("Division Test")
    void testDivision() {
        assertEquals(2, calc.divide(6, 3), "6 / 3 should be 2");
        assertEquals(5, calc.divide(10, 2), "10 / 2 should be 5");
    }

    @Test
    @Order(4)
    @DisplayName("Division by Zero Test")
    void testDivisionByZero() {
        assertThrows(ArithmeticException.class, () -> calc.divide(5, 0), "Division by zero should throw an exception");
    }

    @Test
    @Order(5)
    @DisplayName("Factorial Test")
    void testFactorial() {
        assertEquals(1, calc.factorial(0), "0! should be 1");
        assertEquals(1, calc.factorial(1), "1! should be 1");
        assertEquals(120, calc.factorial(5), "5! should be 120");
    }

    @Test
    @Order(6)
    @DisplayName("Factorial Negative Input Test")
    void testFactorialNegativeInput() {
        assertThrows(IllegalArgumentException.class, () -> calc.factorial(-3), "Negative inputs should throw an exception");
    }
    
    @Test
    @Order(7)
    @DisplayName("Disabled Test Example")
    @Disabled("Fix: adjust the expected value")
    void testDisabledExample() {
        assertEquals(10, calc.add(4, 5), "This Test is Disabled.");
    }

    @Test
    @Order(8)
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    @DisplayName("Test slow calculation")
    void testSlowCalculation() {
        assertEquals(2000, calc.add(1000, 1000), "1000 + 1000 should be 2000");
    }
    
    @AfterEach
    void tearDown() {
        System.out.println("Test completed :)");
        System.out.println(Thread.currentThread().getName() + " finished test.");
    }
}