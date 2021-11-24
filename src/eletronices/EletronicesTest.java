package eletronices;

import homepage.Utilities;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EletronicesTest extends Utilities {


    String baseUrl = "https://demo.nopcommerce.com/";

    @Before
    public void setBrowser() {
        openBrowser(baseUrl);
    }

    @Test
    public void verifyUserShouldNavigateToCellPhonesPageSuccessfully() {
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.xpath("//ul[@class='top-menu notmobile']//a[normalize-space()='Electronics']"))).build().perform();
        actions.moveToElement(driver.findElement(By.xpath("//ul[@class='top-menu notmobile']//a[normalize-space()='Cell phones']"))).click().build().perform();
        verifyPageNavigation("Cell phones", By.xpath("//h1[contains(text(),'Cell phones')]"), "User is not on cell phones");

    }

    @Test
    public void verifyThatTheProductAddedSuccessfullyAndPlaceOrderSuccessfully() throws InterruptedException {
        verifyUserShouldNavigateToCellPhonesPageSuccessfully();
        clickOnElement(By.xpath("//a[contains(text(),'List')]"));
        Thread.sleep(3000);
        driver.findElement(By.xpath("//h2[@class='product-title']//a[contains(text(),'Nokia Lumia 1020')]")).click();
        verifyPageNavigation("Nokia Lumia 1020", By.xpath("//h1[contains(text(),'Nokia Lumia 1020')]"), "Nokia Lumia 1020 is not selected");
        verifyPageNavigation("$349.00", By.xpath("//span[contains(text(),'$349.00')]"), "Price fro Nokia Lumia 1020 is incorrect");

        mouseHoverOn("//input[@id='product_enteredQuantity_20']");

        Actions actionsNew = new Actions(driver);
        actionsNew.doubleClick(driver.findElement(By.xpath("//input[@id='product_enteredQuantity_20']"))).perform();
        Thread.sleep(5000);
        driver.findElement((By.xpath("//input[@id='product_enteredQuantity_20']"))).sendKeys("2");
        Thread.sleep(5000);

        clickOnElement(By.xpath("//button[@id='add-to-cart-button-20']"));
        verifyPageNavigation("The product has been added to your shopping cart", By.xpath("//p[@class='content' and contains(text(),'The product has been added to your ')]"), "Order unsuccessful");
        clickOnElement(By.xpath("//span[@title='Close']"));

        mouseHoverOn("//span[@class='cart-label']");
        Thread.sleep(5000);

        mouseHoverAndClick("//button[contains(text(),'Go to cart')]");
        Thread.sleep(5000);

        verifyPageNavigation("Shopping cart", By.xpath("//h1[contains(text(),'Shopping cart')]"), "User is not on shopping cart");

        String expQty = "2";
        String actualQty = driver.findElement(By.xpath("//tbody/tr[1]/td[5]/input[1]")).getAttribute("value");
        Thread.sleep(5000);
        Assert.assertEquals("Quantity is not 2", expQty, actualQty);

        verifyPageNavigation("$698.00", By.xpath("//span[@class='product-subtotal' and contains(text(),'$698.00')]"), "Total is not correct");

        clickOnElement(By.xpath("//input[@id='termsofservice']"));
        clickOnElement(By.xpath("//button[@id='checkout']"));
        verifyPageNavigation("Welcome, Please Sign In!", By.xpath("//h1[contains(text(),'Welcome, Please Sign In!')]"), "User not on welcome page");

        clickOnElement(By.xpath("//button[contains(text(),'Register')]"));
        verifyPageNavigation("Register", By.xpath("//h1[contains(text(),'Register')]"), "User is not navigated to Register page");

        sendTextToElement(By.xpath("//input[@id='FirstName']"), "John");
        sendTextToElement(By.xpath("//input[@id='LastName']"), "Peter");

        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        String email = "johnpeter" + timeStamp + "@test.com";
        driver.findElement(By.xpath("//input[@id='Email']")).sendKeys(email);
        Thread.sleep(3000);

        sendTextToElement(By.xpath("//input[@id='Password']"), "Johnpeter@1209");
        sendTextToElement(By.xpath("//input[@id='ConfirmPassword']"), "Johnpeter@1209");
        clickOnElement(By.xpath("//button[@id='register-button']"));
        verifyPageNavigation("Your registration completed", By.xpath("//div[contains(text(),'Your registration completed')]"), "Registration failed");
        clickOnElement(By.xpath("//a[contains(text(),'Continue')]"));
        verifyPageNavigation("Shopping cart", By.xpath("//h1[contains(text(),'Shopping cart')]"), "User not on shopping cart");
        clickOnElement(By.xpath("//input[@id='termsofservice']"));
        clickOnElement(By.xpath("//button[@id='checkout']"));

        selectFromDropDown(By.xpath("//select[@id='BillingNewAddress_CountryId']"), "1");
        selectFromDropDown(By.xpath("//select[@id='BillingNewAddress_StateProvinceId']"), "16");
        driver.findElement(By.xpath("//input[@id='BillingNewAddress_City']")).sendKeys("New York");
        driver.findElement(By.xpath("//input[@id='BillingNewAddress_Address1']")).sendKeys("748 Lower River Street");
        Thread.sleep(3000);

        driver.findElement(By.xpath("//input[@id='BillingNewAddress_ZipPostalCode']")).sendKeys("NY 10009");
        driver.findElement(By.xpath("//input[@id='BillingNewAddress_PhoneNumber']")).sendKeys("+16469899890");
        Thread.sleep(3000);

        clickOnElement(By.xpath("//button[@onclick='Billing.save()']"));
        clickOnElement(By.xpath("//input[@id='shippingoption_2']"));
        Thread.sleep(3000);
        clickOnElement(By.xpath("//button[@class='button-1 shipping-method-next-step-button']"));
        clickOnElement(By.xpath("//input[@id='paymentmethod_1']"));
        clickOnElement(By.xpath("//button[@class='button-1 payment-method-next-step-button']"));
        Thread.sleep(3000);

        sendTextToElement(By.xpath("//input[@id='CardholderName']"), "Mark Peter");
        driver.findElement(By.id("CardNumber")).sendKeys("4658 5987 2846 6012");
        selectFromDropDown(By.xpath("//select[@id='ExpireYear']"), "2023");
        sendTextToElement(By.xpath("//input[@id='CardCode']"), "963");
        clickOnElement(By.xpath("//button[@class='button-1 payment-info-next-step-button']"));
        Thread.sleep(3000);

        verifyPageNavigation("Credit Card", By.xpath("//span[contains(text(),'Credit Card')]"), "Payment method is not credit card");
        verifyPageNavigation("2nd Day Air", By.xpath("//span[normalize-space()='2nd Day Air']"), "Shipping method is not second day air");
        Thread.sleep(3000);

        verifyPageNavigation("$698.00", By.xpath("//span[@class='value-summary']//strong[contains(text(),'$698.00')]"), "Total is incorrect");

        clickOnElement(By.xpath("//button[normalize-space()='Confirm']"));

        verifyPageNavigation("Thank you", By.xpath("//h1[contains(text(),'Thank you')]"), "Thank you");
        Thread.sleep(3000);

        verifyPageNavigation("Your order has been successfully processed!", By.xpath("//strong[contains(text(),'Your order has been successfully processed!')]"), "Order is not confirmed");
        clickOnElement(By.xpath("//button[contains(text(),'Continue')]"));
        verifyPageNavigation("Welcome to our store", By.xpath("//h2[contains(text(),'Welcome to our store')]"), "Welcome failed");
        clickOnElement(By.xpath("//a[contains(text(),'Log out')]"));
        Thread.sleep(3000);

        String URL = driver.getCurrentUrl();
        Assert.assertEquals("Logout unsuccessful", URL, "https://demo.nopcommerce.com/");
    }

        @After
        public void tearDown() {
            closeBrowser();
        }
    }
