package mission;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import org.testng.Assert;
import pages.CartPage;
import pages.CheckoutPage;
import pages.InventoryPage;
import pages.LoginPage;

import java.util.List;


public class UIStepDefinitions {

    LoginPage loginPage;
    InventoryPage inventoryPage;
    CartPage cartPage;
    CheckoutPage checkoutPage;

    double calculatedItemTotal = 0.0;

    public UIStepDefinitions() {
        loginPage = new LoginPage(Hook.driver);
        inventoryPage = new InventoryPage(Hook.driver);
        cartPage = new CartPage(Hook.driver);
        checkoutPage = new CheckoutPage(Hook.driver);
    }



    @And("I login in with the following details")
    public void login_with_details(DataTable table) throws InterruptedException {
        List<List<String>> data = table.asLists(String.class);
        String username = data.get(1).get(0);
        String password = data.get(1).get(1);

        loginPage.login(username, password);

    }

    @And("I add the following items to the basket")
    public void add_items_to_basket(DataTable table) throws InterruptedException {
        List<List<String>> items = table.asLists(String.class);

        for (int i = 0; i < items.size(); i++) {
            inventoryPage.addItem(items.get(i).get(0));
        }
//        Thread.sleep(10000);

    }

    @And("I should see 4 items added to the shopping cart")
    public void verify_cart_count_4() {
        Assert.assertEquals(inventoryPage.getCartCount(), 4);
    }


    @And("I click on the shopping cart")
    public void click_cart() {
        inventoryPage.openCart();
    }

    @And("I verify that the QTY count for each item should be 1")
    public void verify_qty_for_each_item() {

        List<Integer> quantities = cartPage.getAllItemQuantities();

        for (int qty : quantities) {
            Assert.assertEquals(qty, 1);
        }
    }


    @And("I remove the following item:")
    public void remove_item(DataTable table) {
        String item = table.asList(String.class).get(0);
        cartPage.removeItem(item);
    }


    @And("I click on the CHECKOUT button")
    public void click_checkout() {
        cartPage.clickCheckout();

    }

    @And("I should see 3 items added to the shopping cart")
    public void verify_cart_count_3() {
        Assert.assertEquals(inventoryPage.getCartCount(), 3);
    }


    @And("I type \"FirstName\" for First Name")
    public void enter_first_name() {
        checkoutPage.enterDetails("FirstName", "", "");
    }


    @And("I type \"LastName\" for Last Name")
    public void enter_last_name() {
        checkoutPage.enterDetails("", "LastName", "");
    }


    @When("I click on the CONTINUE button")
    public void click_continue() {
        checkoutPage.clickContinue();

    }

    @Then("Item total will be equal to the total of items on the list")
    public void verify_item_total() {
        calculatedItemTotal = checkoutPage.getItemTotal();
        Assert.assertTrue(calculatedItemTotal > 0);
    }

    @And("a Tax rate of 8 % is applied to the total")
    public void verify_tax() {
        double expectedTax = calculatedItemTotal * 0.08;
        double actualTax = checkoutPage.getTax();
        Assert.assertEquals(actualTax, expectedTax, 0.01);
    }
    @And("I type {string} for ZIP Postal Code")
    public void enter_zip(String zip) {
        checkoutPage.enterDetails("", "", zip);
    }


}

