package selenium.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
    public static WebDriver driver;
    private static WebElement element;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public static WebElement ip_username() {
        element = driver.findElement(By.xpath("//input[@id='usr']"));
        return element;
    }

    public static WebElement ip_password() {
        element = driver.findElement(By.xpath("//input[@id='pwd']"));
        return element;
    }

    public static WebElement btn_login() {
        element = driver.findElement(By.xpath("//input[@type='submit']"));
        return element;
    }

    public static WebElement txt_successfully() {
        element = driver.findElement(By.xpath("//h3[@class='success']"));
        return element;
    }

}
