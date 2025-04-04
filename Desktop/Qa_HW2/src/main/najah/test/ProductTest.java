package main.najah.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import main.najah.code.Product;

import java.util.concurrent.TimeUnit;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Execution(ExecutionMode.CONCURRENT) 
public class ProductTest {

    private Product product;

    @BeforeAll
    static void initAll() {
        System.out.println("Testing has start...");
    }

    @BeforeEach
    void setUp() {
        product = new Product("Laptop", 1000.0);
        System.out.println("A new product has been created");
    }

    @Test
    @Order(1)
    @DisplayName("Product Creation Test")
    void testProductCreation() {
        assertAll("Product Properties",
            () -> assertEquals("Laptop", product.getName()),
            () -> assertEquals(1000.0, product.getPrice()),
            () -> assertEquals(0, product.getDiscount())
        );
    }

    @Test
    @Order(2)
    @DisplayName("Negative Price Test")
    void testNegativePriceShouldThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Product("Phone", -500.0);
        });
        assertEquals("Price must be non-negative", exception.getMessage());
    }

    @ParameterizedTest
    @Order(3)
    @DisplayName("Apply Valid Discount Test")
    @ValueSource(doubles = {10, 20, 30, 40, 50})
    void testApplyValidDiscount(double discount) {
        product.applyDiscount(discount);
        assertEquals(discount, product.getDiscount());
        assertTrue(product.getFinalPrice() <= 1000.0);
    }

    @Test
    @Order(4)
    @DisplayName("Reject invalid discounts")
    void testApplyInvalidDiscountShouldThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            product.applyDiscount(60);
        });
        assertEquals("Invalid discount", exception.getMessage());
    }

    @Test
    @Order(5)
    @DisplayName("Final Price Calculation Test After Discount")
    void testFinalPriceCalculation() {
        product.applyDiscount(10);
        assertEquals(900.0, product.getFinalPrice());
    }

    @Test
    @Order(6)
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)  // Apply timeout to the entire test
    @DisplayName("Timeout Test")
    void testFinalPriceCalculationPerformance() {
        product.applyDiscount(10);
        double price = product.getFinalPrice();
        assertEquals(900.0, price);
    }

    @Test
    @Order(7)
    @Disabled("Fix: adjust the expected value")
    @DisplayName("Test disabled for later update")
    void testDisabledCase() {
    	 product.applyDiscount(20); //Discount 20%
    	    double finalPrice = product.getFinalPrice();
    	    
    	    // This test is disabled because it expects 1800, but the calculations may need updating.
    	    assertEquals(1800.0, finalPrice, "The final price calculation is incorrect.");
    	    
        fail("Must update expected for this test.");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Test execution completed.");
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("All tests have been completed!");
    }
}