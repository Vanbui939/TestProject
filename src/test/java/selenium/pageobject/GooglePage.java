package selenium.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import selenium.core.BaseLocator;
import selenium.core.BasePage;


public class GooglePage extends BasePage {
    public Page page;
    public GooglePage(){
        super();
       page = new Page();
    }
    class Page{
        public BaseLocator txtSearch(){
            return new BaseLocator(By.cssSelector("[title=\"Tìm kiếm\"]"));
        }
        public BaseLocator lblResult(){
            return new BaseLocator(By.cssSelector("div#result-stats"));
        }
        public BaseLocator btnSearch(){
            return new BaseLocator(By.xpath("(//input[@name=\"btnK\"])[2]"));
        }
    }
        public void inputKeyWord(String key) throws InterruptedException{
            page.txtSearch().waitUntilVisible().sendKeys(key);
    }
    public void clickSearch() throws InterruptedException{
        page.btnSearch().sendKeys(Keys.ENTER);
    }

        public void verifyDisplayResult() throws InterruptedException {
            page.lblResult().waitUntilVisible(60).hightlight();
    }



    @Override
    public void openPage(String url) {
        getDriver().get(url);
    }

}
