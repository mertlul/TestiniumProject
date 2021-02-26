import org.junit.Assert;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.service.DriverService;

public class main {

    public static void main(String[] args) {

        Result result = JUnitCore.runClasses(GittiGidiyorTest.class);

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
    }
}
