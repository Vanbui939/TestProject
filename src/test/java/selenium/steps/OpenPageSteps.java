package selenium.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import selenium.pageobject.FacebookPage;
import selenium.pageobject.GooglePage;

import java.util.ArrayList;

public class OpenPageSteps {

    GooglePage googlePage;
    FacebookPage facebookPage;
    public OpenPageSteps(){
    googlePage = new GooglePage();
    facebookPage = new FacebookPage();

    }

    @When("Open Google")
    public void openGoogle() {
        System.out.println ("https://www.google.com/");
        googlePage.openPage("https://www.google.com/");

    }


    @And("Open new tab and open Facebook")
    public void openNewTabAndOpenFacebook() {
        googlePage.switchToNewWindow();
        facebookPage.openPage("https://www.facebook.com/");



    }

    @And("Input keyword to search")
    public void inputKeywordToSearch() throws InterruptedException {
        googlePage.inputKeyWord();
    }

    @And("Click Search")
    public void clickSearch() throws InterruptedException {
        googlePage.enter();
    }

    @Then("Show Result")
    public void showResult() throws InterruptedException {
        googlePage.verifyDisplayResult();
    }
}
