package cucumber.runner.web;

import cucumber.api.CucumberOptions;
import cucumber.runner.core.WebRunner;
@CucumberOptions (
        features = {"classpath:resources/features/test.feature"},
        plugin = {"pretty", "html:target/cucumber-reports/web/openPage",
                "json:target/cucumber-reports/web/openPage/Cucumber.json"},
        tags = {},
        glue = {"selenium/steps"},
        monochrome = true, strict = true

)

public class OpenPageTest extends WebRunner {

}
