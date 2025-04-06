package main.najah.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import main.najah.code.Recipe;
import main.najah.code.RecipeException;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Execution(ExecutionMode.CONCURRENT) 
public class RecipeTest {
    
    Recipe recipe;

    @BeforeAll
    static void initAll() {
        System.out.println("Testing has start...");
    }
    
    @BeforeEach
    void setUp() {
        recipe = new Recipe(); 
    }

    @Test
    @Order(1)
    @DisplayName("Test Name Getter and Setter")
    void testName() {
        recipe.setName("Coffee");
        assertEquals("Coffee", recipe.getName(), "The name should be 'Coffee'");
    }

    @Test
    @Order(2)
    @DisplayName("Test Price Getter and Setter")
    void testPrice() {
        assertThrows(RecipeException.class, () -> recipe.setPrice("-10"), "Price must be a positive integer");
        assertDoesNotThrow(() -> recipe.setPrice("50"), "Price should be set correctly");
        assertEquals(50, recipe.getPrice(), "Price should be 50");
    }

    @Test
    @Order(3)
    @DisplayName("Test Coffee Amount Getter and Setter")
    void testAmtCoffee() {
        assertThrows(RecipeException.class, () -> recipe.setAmtCoffee("-5"), "Units of coffee must be a positive integer");
        assertDoesNotThrow(() -> recipe.setAmtCoffee("10"), "Coffee amount should be set correctly");
        assertEquals(10, recipe.getAmtCoffee(), "Coffee amount should be 10");
    }

    @Test
    @Order(4)
    @DisplayName("Test Milk Amount Getter and Setter")
    void testAmtMilk() {
        assertThrows(RecipeException.class, () -> recipe.setAmtMilk("-5"), "Units of milk must be a positive integer");
        assertDoesNotThrow(() -> recipe.setAmtMilk("20"), "Milk amount should be set correctly");
        assertEquals(20, recipe.getAmtMilk(), "Milk amount should be 20");
    }

    @Test
    @Order(5)
    @DisplayName("Test Sugar Amount Getter and Setter")
    void testAmtSugar() {
        assertThrows(RecipeException.class, () -> recipe.setAmtSugar("-1"), "Units of sugar must be a positive integer");
        assertDoesNotThrow(() -> recipe.setAmtSugar("5"), "Sugar amount should be set correctly");
        assertEquals(5, recipe.getAmtSugar(), "Sugar amount should be 5");
    }

    @Test
    @Order(6)
    @DisplayName("Test Chocolate Amount Getter and Setter")
    void testAmtChocolate() {
        assertThrows(RecipeException.class, () -> recipe.setAmtChocolate("-2"), "Units of chocolate must be a positive integer");
        assertDoesNotThrow(() -> recipe.setAmtChocolate("7"), "Chocolate amount should be set correctly");
        assertEquals(7, recipe.getAmtChocolate(), "Chocolate amount should be 7");
    }

    @Test
    @Order(7)
    @DisplayName("Test Object Equality")
    void testEquals() {
        Recipe recipe2 = new Recipe();
        recipe2.setName("Coffee");
        recipe.setName("Coffee");
        assertTrue(recipe.equals(recipe2), "Recipes with the same name should be equal");
    }

    @Test
    @Order(8)
    @DisplayName("Test Object HashCode")
    void testHashCode() {
        Recipe recipe2 = new Recipe();
        recipe2.setName("Coffee");
        recipe.setName("Coffee");
        assertEquals(recipe.hashCode(), recipe2.hashCode(), "Hash codes should match for recipes with the same name");
    }

    @Test
    @Order(9)
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    @DisplayName("Test Performance of Recipe")
    void testPerformance() {
        assertTimeout(Duration.ofMillis(500), () -> {
            recipe.setName("Latte");
            recipe.setPrice("100");
            recipe.setAmtCoffee("10");
            recipe.setAmtMilk("5");
            recipe.setAmtSugar("2");
            recipe.setAmtChocolate("3");
        });
    }

    @Test
    @Order(10)
    @Disabled("Test disabled due to incomplete validation logic")
    @DisplayName("Test Disabled for Future Fix")
    void testDisabledCase() {
        Recipe recipe2 = new Recipe();
        recipe2.setName("Sana");
        recipe.setName("Coffee");
        assertTrue(recipe.equals(recipe2), "Recipes with the same name should be equal");
        fail("Test logic not yet implemented for validation.");
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