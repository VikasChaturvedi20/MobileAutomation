package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CalculatorPage;

public class CalculatorTest extends BaseTest {

    public CalculatorTest() {
        System.out.println("🟢 CalculatorTest constructor called");
    }

    @Test
    public void testAddition() {
        System.out.println("➡️ testAddition method started");

        // Initialize Page Object
        CalculatorPage calc = new CalculatorPage(driver);

        try {
            // Perform 5 + 9
            System.out.println("📱 Pressing 5");
            calc.press5();

            System.out.println("➕ Pressing +");
            calc.pressAdd();

            System.out.println("📱 Pressing 9");
            calc.press9();

            System.out.println("✅ Pressing =");
            calc.pressEqual();

            // Capture result
            String result = calc.getResult();
            System.out.println("📊 Calculator displayed result: " + result);

            // Assertion
            Assert.assertEquals(result, "14", "❌ Addition result is incorrect!");
            System.out.println("🎉 Test Passed: 5 + 9 = " + result);

        } catch (Exception e) {
            System.err.println("🚨 Test failed due to exception: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Test failed with exception: " + e.getMessage());
        }
    }
}
