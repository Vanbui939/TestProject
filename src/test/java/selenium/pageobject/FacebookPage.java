package selenium.pageobject;

import selenium.core.BasePage;


public class FacebookPage extends BasePage {
    public Page page;
    public FacebookPage(){
        super();
       page = new Page();
    }
    class Page{

    }
    @Override
    public void openPage(String url) {
        getDriver().get(url);
    }

}
