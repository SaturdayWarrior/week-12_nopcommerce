package homepage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

public class TopMenuTest extends Utilities{

    String baseUrl = "https://demo.nopcommerce.com/";

    @Before
    public void setBrowser() {
        openBrowser(baseUrl);
    }

    @Test
    public void verifyTestOne() {
        selectMenu("Computers");
        verifyPageNavigation("Computers", By.xpath("//ul[@class='top-menu notmobile']//a[normalize-space()='Computers']"), "Your are on Computers page");
    }

    @Test
    public void verifyTestTwo() {
        selectMenu("Electronics");
        verifyPageNavigation("Electronics", By.xpath("//ul[@class='top-menu notmobile']//a[normalize-space()='Electronics']"), "Your are on Electronics page");
    }

    @Test
    public void verifyTestThree() {
        selectMenu("Apparel");
        verifyPageNavigation("Apparel", By.xpath("//ul[@class='top-menu notmobile']//a[normalize-space()='Apparel']"), "Your are on Apparel page");
    }

    @Test
    public void verifyTestFour() {
        selectMenu("Digital downloads");
        verifyPageNavigation("Digital downloads", By.xpath("//ul[@class='top-menu notmobile']//a[normalize-space()='Digital downloads']"), "Your are on Digital downloads page");
    }

    @Test
    public void verifyTestFive() {
        selectMenu("Books");
        verifyPageNavigation("Books", By.xpath("//ul[@class='top-menu notmobile']//a[normalize-space()='Books']"), "Your are on Books page");
    }

    @Test
    public void verifyTestSix() {
        selectMenu("Jewelry");
        verifyPageNavigation("Jewelry", By.xpath("//ul[@class='top-menu notmobile']//a[normalize-space()='Jewelry']"), "Your are on Jewelry page");
    }

    @Test
    public void verifyTestSeven() {
        selectMenu("Gift Cards");
        verifyPageNavigation("Gift Cards", By.xpath("//ul[@class='top-menu notmobile']//a[normalize-space()='Gift Cards']"), "Your are on Gift Cards page");
    }

    @After
    public void tearDown() {
        closeBrowser();
    }
}