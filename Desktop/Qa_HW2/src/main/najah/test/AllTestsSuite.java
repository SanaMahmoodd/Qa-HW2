package main.najah.test;

import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Execution(ExecutionMode.CONCURRENT) 
@Suite
@SelectClasses({
    UserServiceSimpleTest.class, 
    CalculatorTest.class,
    ProductTest.class,
    RecipeBookTest.class,
    RecipeTest.class
})

class AllTestsSuite {

}
