package computer;

import homepage.Utilities;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class TestSuite extends Utilities {

    String baseUrl = "https://demo.nopcommerce.com/";

    @Before
    public void setBrowser() {
        openBrowser(baseUrl);
    }

    @Test
    public void verifyProductArrangeInAlphabeticalOrder() {
        Actions action = new Actions(driver);
        action.moveToElement(driver.findElement(By.xpath("(//a[normalize-space()='Computers'])[1]"))).build().perform();
        clickOnElement(By.xpath("//ul[@class='top-menu notmobile']//a[normalize-space()='Desktops']"));

        WebElement dropdown = driver.findElement(By.xpath("//select[@id='products-orderby']"));
        Select select = new Select(dropdown);
        select.selectByValue("6");

        List<WebElement> products = driver.findElements(By.xpath("//div[@class='products-wrapper']//descendant::div[@class='item-box']"));
        List<String> names = new ArrayList<String>();
        for (WebElement e : products) {
            names.add(e.getText());
        }
        List<String> sortedNames = new ArrayList<String>(names);
        Collections.sort(sortedNames);
        System.out.println(sortedNames.equals(names));

        Assert.assertEquals("Sorting product names from Z to A is not working", names, sortedNames);
    }@Test
   public void verifyProductAddedToShoppingCartSuccessFully() throws InterruptedException {


        mouseHoverOn("(//a[normalize-space()='Computers'])[1]");
        clickOnElement(By.xpath("//ul[@class='top-menu notmobile']//a[normalize-space()='Desktops']"));
        selectFromDropDown(By.xpath("//select[@id='products-orderby']"), "5");
        sortProductsCompareList("Name: A - Z");
        driver.findElement(By.xpath("//button[contains(@onclick,'return AjaxCart.addproducttocart_catalog(\"/addproducttocart/catalog/1/1/1\"),!1')]")).click();
        Thread.sleep(2000);
        verifyPageNavigation("Build your own computer", By.xpath("//h1[contains(text(),'Build your own computer')]"), "Not added to cart");
        WebElement dropDown1 = driver.findElement(By.xpath("//select[@id='product_attribute_1']"));
        Select select1 = new Select(dropDown1);
        select1.selectByValue("1");

        //Select "8GB [+$60.00]" using Select class
        selectFromDropDown(By.xpath("//select[@id='product_attribute_1']"), "1");
        selectFromDropDown(By.xpath("//select[@id='product_attribute_2']"), "5");

        clickOnElement(By.xpath("//input[@id='product_attribute_3_7']"));
        clickOnElement(By.id("product_attribute_4_9"));
        Thread.sleep(3000);
        clickOnElement(By.id("product_attribute_5_12"));
        Thread.sleep(5000);
        verifyPageNavigation("$1,475.00", By.xpath("//span[@id='price-value-1']"), "The price is not as per the specifications");

        Thread.sleep(5000);
        clickOnElement(By.xpath("//button[@id='add-to-cart-button-1']"));
        Thread.sleep(5000);
        verifyPageNavigation("The product has been added to your shopping cart", By.xpath("//p[@class='content' and contains(text(),'The product has been added to your ')]"), "Product has not been added to cart.");
        Thread.sleep(5000);
        mouseHoverOn("//span[contains(text(),'Shopping cart')]");
        Thread.sleep(5000);
        clickOnElement(By.xpath("//button[contains(text(),'Go to cart')]"));
        Thread.sleep(5000);
        verifyPageNavigation("Shopping cart", By.xpath("//h1[contains(text(),'Shopping cart')]"), "User is not on the shopping cart page");

        Thread.sleep(5000);
        Actions actionsOb = new Actions(driver);
        actionsOb.doubleClick(driver.findElement(By.xpath("//input[@class='qty-input']"))).perform();
        Thread.sleep(5000);
        driver.findElement((By.xpath("//input[@class='qty-input']"))).sendKeys("2");
        Thread.sleep(5000);

        driver.findElement(By.xpath("//button[contains(text(),'Update shopping cart')]")).click();
        // clickOnElement(By.xpath("//button[contains(text(),'Update shopping cart')]"));
        Thread.sleep(5000);
        //Verify the Total"$2,950.00"
        verifyPageNavigation("$2,950.00", By.xpath("//tr[@class='order-total']//td[@class='cart-total-right']"), "Quantity of products is not updated in the cart");

        driver.findElement(By.xpath("//input[@id='termsofservice']")).click();
        clickOnElement(By.xpath("//button[@id='checkout']"));
        Thread.sleep(3000);

        //Verify the Text “Welcome, Please Sign In!”
        verifyPageNavigation("Welcome, Please Sign In!", By.xpath("//h1[normalize-space()='Welcome, Please Sign In!']"), "Checkout unsuccessful");
        clickOnElement(By.xpath("//button[contains(text(),'Checkout as Guest')]"));
        //Verify the Text “Welcome, Please Sign In!”
        clickOnElement(By.id("BillingNewAddress_FirstName"));
        sendTextToElement(By.id("BillingNewAddress_FirstName"),"Mark");
        sendTextToElement(By.id("BillingNewAddress_LastName"),"parker");
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        String email = "test" + timeStamp + "@test.com";
        driver.findElement(By.id("BillingNewAddress.Email")).sendKeys("email");
        WebElement dropdown6 = driver.findElement(By.id("BillingNewAddress_CountryId"));
        Select selectObj = new Select(dropdown6);
        selectObj.selectByValue("1");
        sendTextToElement(By.id("BillingNewAddress_StateProvinceId"),"texas");
        sendTextToElement(By.id("BillingNewAddress_Address1"),"123");
        sendTextToElement(By.id("BillingNewAddress_ZipPostalCode"),"08453");
        sendTextToElement(By.id("BillingNewAddress.PhoneNumber"),"(555) 555-1234");
        clickOnElement(By.xpath("//button[@onclick='Billing.save()']"));
        Thread.sleep(3000);
        driver.findElement(By.xpath("//input[@id='shippingoption_1']")).click();
        driver.findElement(By.xpath("//button[@class='button-1 shipping-method-next-step-button']")).click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//input[@id='paymentmethod_1']")).click();
        driver.findElement(By.xpath("//button[@class='button-1 payment-method-next-step-button']")).click();
        Thread.sleep(3000);
        selectFromDropDown(By.xpath("//select[@id='CreditCardType']"), "MasterCard");
        sendTextToElement(By.id("CardholderName"),"Ram Patel");
        sendTextToElement(By.id("CardNumber"),"4751 3002 2642 4221");
        selectFromDropDown(By.xpath("//select[@id='ExpireMonth']"), "2");
        selectFromDropDown(By.xpath("//select[@id='ExpireYear']"), "2023");
        sendTextToElement(By.xpath("//input[@id='CardCode']"), "267");
        Thread.sleep(3000);
        clickOnElement(By.xpath("//button[@class='button-1 payment-info-next-step-button']"));

        Thread.sleep(3000);

        verifyPageNavigation("Credit Card", By.xpath("//span[contains(.,'Credit Card')]"), "Selected payment method is not credit card");
        Thread.sleep(3000);

        verifyPageNavigation("Next Day Air", By.xpath("//span[normalize-space()='Next Day Air']"), "Please select shipping method as Next Day Air");
        Thread.sleep(3000);

        verifyPageNavigation("$2,950.00", By.xpath("//span[@class='value-summary']//strong[contains(text(),'$2,950.00')]"), "Total is not $2,950.00");
        Thread.sleep(3000);

        clickOnElement(By.xpath("//button[normalize-space()='Confirm']"));
        Thread.sleep(3000);

        verifyPageNavigation("Thank you", By.xpath("//h1[normalize-space()='Thank you']"), "Order has not been placed successfully");
        Thread.sleep(3000);

        verifyPageNavigation("Your order has been successfully processed!", By.xpath("//strong[normalize-space()='Your order has been successfully processed!']"), "Order processing is unsuccessful");
        Thread.sleep(3000);

        clickOnElement(By.xpath("//button[normalize-space()='Continue']"));
        Thread.sleep(3000);

        verifyPageNavigation("Welcome to our store", By.xpath("//h2[normalize-space()='Welcome to our store']"), "User is not on NopCom welcome page");

    }

    @After
    public void tearDown() {
        closeBrowser();
    }
}



