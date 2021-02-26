import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class GittiGidiyorTest {

    private WebDriver driver = null;
    private AssertBot bot = null;

    public GittiGidiyorTest()
    {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\BobLoblaw\\Desktop\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        driver = new ChromeDriver(options);
        driver.manage().window().setSize(new Dimension(1920,1080));
        bot = new AssertBot();
    }

    @Test
    public void mainPage() throws InterruptedException {

        //Test the Home Page of gittigidiyor.com.
        driver.get("https://www.gittigidiyor.com");
        bot.makeAssertion(driver.getCurrentUrl(), "https://www.gittigidiyor.com/");

        //Test the Login feature.
        driver.navigate().to("https://www.gittigidiyor.com/uye-girisi");

        WebElement username = driver.findElement(By.id("L-UserNameField"));
        WebElement password = driver.findElement(By.id("L-PasswordField"));
        WebElement loginButton = driver.findElement(By.id("gg-login-enter"));

        String userUsername = "Please enter your username.";
        String userPassword = "Please enter your password.";
        username.sendKeys(userUsername);
        password.sendKeys(userPassword);
        loginButton.click();

        bot.makeAssertion(driver.getCurrentUrl(), "https://www.gittigidiyor.com/");

        //Test the searching capability.

        WebElement searchBox = driver.findElement(By.xpath("//*[@id=\"main-header\"]/div[3]/div/div/div/div[2]/form/div/div[1]/div[2]/input"));
        WebElement searchButton = driver.findElement(By.xpath("//*[@id=\"main-header\"]/div[3]/div/div/div/div[2]/form/div/div[2]/button"));

        searchBox.sendKeys("bilgisayar");
        searchButton.click();

        driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.END);

        WebElement secondPage = driver.findElement(By.xpath("//*[@id=\"best-match-right\"]/div[5]/ul/li[2]/a"));

        Actions actions = new Actions(driver);
        actions.moveToElement(secondPage);
        actions.perform();
        secondPage.click();

        bot.makeAssertion(driver.getCurrentUrl(), "https://www.gittigidiyor.com/arama/?k=bilgisayar&sf=2");

        //Add an item to the basket and check the price.

        List<WebElement> items = driver.findElements(By.xpath("//*[@id=\"best-match-right\"]/div[3]/div[2]/ul/li"));
        Random r = new Random();

        WebElement selectedItem = items.get(r.nextInt(48));
        selectedItem.click();

        WebElement addToBasketButton2 = driver.findElement(By.id("add-to-basket"));
        String price = driver.findElement(By.id("sp-price-highPrice")).getText();
        addToBasketButton2.click();

        Thread.sleep(1000);
        driver.navigate().to("https://www.gittigidiyor.com/sepetim");

        String price2 = driver.findElement(By.xpath("//*[@id=\"cart-price-container\"]/div[3]/p")).getText();

        bot.makeAssertion(price, price2);

        //Increase item count to 2 and check.

        Select select = new Select(driver.findElement(By.cssSelector("select[class='amount']")));
        select.selectByIndex(1);
        bot.makeAssertion("2", select.getFirstSelectedOption().getText().trim());

        //Delete the item from the basket.

        WebElement delete = driver.findElement(By.cssSelector("i[class='gg-icon gg-icon-bin-medium']"));
        delete.click();

        WebElement emptyBasket = driver.findElement(By.id(("empty-cart-container")));
        String s = "";
        if (emptyBasket != null) {
            s = "empty";
        }
        bot.makeAssertion(s, "empty");

    }

}
