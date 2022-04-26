package selenium.pageobject;

import org.openqa.selenium.By;
import selenium.core.MobileBasePage;
import selenium.core.MobileBaseLocator;

import java.net.MalformedURLException;

public class FlockLogin extends MobileBasePage{ //

    public Page page;

    public FlockLogin() {
        super();
        page = new FlockLogin.Page();
    }
    class Page{
        public MobileBaseLocator btnSignIn(){
            return new MobileBaseLocator(By.xpath("//*[@text=\"Sign In\"]"));
        }
        public MobileBaseLocator txtUsername(){
            return new MobileBaseLocator(By.xpath("//*[@text=\"Email\"]/following-sibling::*[1]/android.widget.EditText"));
        }
        public MobileBaseLocator txtPassword(){
            return new MobileBaseLocator(By.xpath("//*[@text=\"Password\"]/following-sibling::*[1]/android.widget.EditText"));
        }
        public MobileBaseLocator btnLogin(){
            return new MobileBaseLocator(By.xpath("//*[@text=\"Login\"]"));
        }
        public MobileBaseLocator lblErrorMsg(){
            return new MobileBaseLocator(By.xpath("//*[@text=\"Incorrect email or password\"]"));
        }
    }

    public void clickSignInBtn() throws InterruptedException {
        Thread.sleep(3000);
        System.out.println("STEP:   Click Signin Button");
        page.btnSignIn().click();
//        Thread.sleep(9000);
    }
    public void inputUsername (String key) {
        System.out.println("STEP:   Input username");
        page.txtUsername().sendKeys(key);
    }
    public void inputPassword (String key) {
        System.out.println("STEP:   Input password");
        page.txtPassword().sendKeys(key);
    }
    public void clickLoginBtn() {
        System.out.println("STEP:   Click Login button");
        page.btnLogin().click();
    }
    public String verifyErrorMsg() {
        System.out.println("STEP:   Verify error message");
        return page.lblErrorMsg().getText();
//        driver.findElement(By.xpath("//*[@text=\"Incorrect email or password\"]")).getAttribute("text");
    }
}
