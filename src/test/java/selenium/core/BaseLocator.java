package selenium.core;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.testhelper.TestParams;

public class BaseLocator extends By {

    private WebDriver driver = DriverManager.getDriver();
    private By locator;
    private WebDriverWait wait = new WebDriverWait(driver, 10);

    public BaseLocator(By locator) {
        this.locator = locator;
    }

    /**
     * Wait until element is visible on browser using configuration timeout For setting timeout,
     * please type in command line with argument browsertimeout e.g.: -Dbrowsertimeout=30
     *
     * @return {@link BaseLocator}
     */
    public BaseLocator waitUntilVisible() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return this;
    }

    /**
     * Wait until element is visible on browser using specific timeout
     *
     * @param timeout
     * @return {@link BaseLocator}
     */
    public BaseLocator waitUntilVisible(int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return this;
    }

    /**
     * Wait until element text contains specific value
     *
     * @param text
     * @return {@link BaseLocator}
     */
    public BaseLocator waitUntilElementTextContains(String text) {
        wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
        return this;
    }

    /**
     * Wait until element text
     *
     * @param timeout, text
     * @return {@link BaseLocator}
     */
    public BaseLocator waitUntilElementTextContains(int timeout, String text) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
        return this;
    }

    /**
     * Wait until element has attribute contains value
     * <p>
     * Mostly wait for Ajax to load data into element.
     * <p>
     * E.g.: Css class is changing, Text field value e.t.c...
     *
     * @param timeout
     * @param text
     * @return {@link BaseLocator}
     */
    public BaseLocator waitUntilElementAttributeContainsValue(String attribute, String value) {
        wait.until(
                CustomWait.waitUntilElementAttributeValueContains(driver, locator, attribute, value));
        return this;
    }

    /**
     * Wait until element has attribute contains value using specific timeout
     * <p>
     * Mostly wait for Ajax to load data into element.
     * <p>
     * E.g.: Css class is changing, Text field value e.t.c...
     *
     * @param timeout
     * @param text
     * @return {@link BaseLocator}
     */
    public BaseLocator waitUntilElementAttributeContainsValue(int timeout, String attribute,
                                                              String value) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.until(
                CustomWait.waitUntilElementAttributeValueContains(driver, locator, attribute, value));
        return this;
    }

    /**
     * Wait until element has attribute value
     * <p>
     * Mostly wait for Ajax to load data into element.
     * <p>
     * E.g.: Css class is changing, Text field value e.t.c...
     *
     * @param timeout
     * @param text
     * @return {@link BaseLocator}
     */
    public BaseLocator waitUntilElementAttributeValue(String attribute, String value) {
        wait.until(CustomWait.waitUntilElementAttributeValue(driver, locator, attribute, value));
        return this;
    }

    /**
     * Wait until element has attribute value using specific timeout
     * <p>
     * Mostly wait for Ajax to load data into element.
     * <p>
     * E.g.: Css class is changing, Text field value e.t.c...
     *
     * @param timeout
     * @param text
     * @return {@link BaseLocator}
     */
    public BaseLocator waitUntilElementAttributeValue(int timeout, String attribute, String value) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.until(CustomWait.waitUntilElementAttributeValue(driver, locator, attribute, value));
        return this;
    }

    /**
     * Wait until element is clickable on browser using configuration timeout For setting timeout,
     * please type in command line with argument browsertimeout e.g.: -Dbrowsertimeout=30
     *
     * @return {@link BaseLocator}
     */
    public BaseLocator waitUntilClickable() {
        wait.until(ExpectedConditions.elementToBeClickable(locator));
        return this;
    }

    /**
     * Wait until element is clickable on browser using specific timeout
     *
     * @param timeout
     * @return {@link BaseLocator}
     */
    public BaseLocator waitUntilClickable(int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.until(ExpectedConditions.elementToBeClickable(locator));
        return this;
    }

    /**
     * Wait until element is not visible or not present on the DOM using configuration timeout For
     * setting timeout, please type in command line with argument browsertimeout e.g.:
     * -Dbrowsertimeout=30
     *
     * @return {@link BaseLocator}
     */
    public BaseLocator waitUntilInvisible() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
        return this;
    }

    /**
     * Wait until element is not visible or not present on the DOM using specific timeout
     *
     * @param timeout
     * @return {@link BaseLocator}
     */
    public BaseLocator waitUntilInvisible(int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
        return this;
    }

    /**
     * Wait until element is present on the DOM using configuration timeout For setting timeout,
     * please type in command line with argument browsertimeout e.g.: -Dbrowsertimeout=30
     *
     * @return {@link BaseLocator}
     */
    public BaseLocator waitUntilPresent() {
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        return this;
    }

    /**
     * Wait until element is present on the DOM using specific timeout
     *
     * @param timeout
     * @return {@link BaseLocator}
     */
    public BaseLocator waitUntilPresent(int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        return this;
    }

    /**
     * Wait until element has text value
     *
     * @param timeout
     * @return {@link BaseLocator}
     */
    public BaseLocator waitUntilElementText(String text) {
        wait.until(ExpectedConditions.textToBe(locator, text));
        return this;
    }

    /**
     * Wait until element has text value
     *
     * @param timeout
     * @param text
     * @return {@link BaseLocator}
     */
    public BaseLocator waitUntilElementText(int timeout, String text) {
        wait.until(ExpectedConditions.textToBe(locator, text));
        return this;
    }

    public BaseLocator delay(int timeout) throws InterruptedException {
        Thread.sleep(timeout * 1000);
        return  this;
    }

    /**
     * Move to element using {@link Actions}
     *
     * @return {@link BaseLocator}
     */
    public BaseLocator moveToElement() {
        Actions action = new Actions(driver);
        action.moveToElement(driver.findElement(locator)).perform();
        return this;
    }

    /**
     * Move to element using {@link Actions}
     *
     * @return {@link BaseLocator}
     */
    public BaseLocator moveToElement(WebElement element) {
        Actions action = new Actions(driver);
        action.moveToElement(element).perform();
        return this;
    }

    /**
     * Typing on the element, or set its value
     *
     * @param keysToSend character sequence to send to the element
     * @return {@link BaseLocator}
     */
    public BaseLocator sendKeys(CharSequence value) {
        driver.findElement(locator).sendKeys(value);
        return this;
    }

    /**
     * Clear text value
     *
     * @return {@link BaseLocator}
     */
    public BaseLocator clear() {
        driver.findElement(locator).clear();
        return this;
    }

    /**
     * Click on element
     *
     * @return {@link BaseLocator}
     */
    public BaseLocator click() {
        // Try to find element and click on it up to 3 times to prevent case page
        // refresh element
        for (int i = 0; i < 3; i++) {
            try {
                driver.findElement(locator).click();
                break;
            } catch (StaleElementReferenceException | ElementClickInterceptedException e) {
                if (i < 2) {
                    continue;
                } else {
                    throw e;
                }
            }
        }
        return this;
    }

    public BaseLocator clickByJs() {
        WebElement element = driver.findElement(locator);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", element);
        return this;
    }

    public BaseLocator tab() {
        WebElement element = driver.findElement(locator);
        element.sendKeys(Keys.TAB);
        return this;
    }

    public BaseLocator checkEnable() {
        WebElement element = driver.findElement(locator);
        Assert.assertTrue(element.isEnabled(), "OK button is enabled.");
        return this;
    }

    public boolean isEnable() {
        boolean isEnable = false;
        WebElement element = driver.findElement(locator);
        if (element.getAttribute("aria-disabled") == null) {
            if (element.getAttribute("disabled")== null) {
                if (element.isEnabled()) {
                    isEnable = true;
                }
            }
            else if (element.getAttribute("disabled").equals("true")){
                isEnable = false;
            }

        }
        else {
            if (element.getAttribute("aria-disabled").equals("false")) {
                isEnable = true;
            }
        }

        return isEnable;
    }

    public boolean isDisable() {
        boolean isDisable = false;
        WebElement element = driver.findElement(locator);
        if (element.getAttribute("aria-disabled") != null) {
            System.out.println("aria-disabled " + element.getAttribute("aria-disabled") );
            if (element.getAttribute("aria-disabled").equals("true")) {

                isDisable = true;
            }
        }
        if (!element.isEnabled()) {
            isDisable = true;
        }
        return isDisable;
    }

    public boolean verifyElementAbsent() throws Exception {
        try {
            WebElement element = driver.findElement(locator);
            System.out.println("Element Present");
            return false;

        } catch (NoSuchElementException e) {
            System.out.println("Element absent");
            return true;
        }
    }

    public boolean isContainAttribute(String attribute) throws Exception {
        boolean isContain = false;
        WebElement element = driver.findElement(locator);
        if (element.getAttribute(attribute) != null) {
            isContain = true;
        }
        return isContain;
    }


    public int countNumberOfElements() {
        int number = 0;
        List listElement = driver.findElements(locator);
        number = listElement.size();
        return number;

    }

    /**
     * Return Text value of element This method will not return Text value of Text field
     *
     * @return The innerText of this element.
     */
    public String getText() {
        return driver.findElement(locator).getText();
    }
    public String getTextNode(WebElement element) {
        String text = element.getText().trim();
        List<WebElement> children = element.findElements(By.xpath("./*"));
        for (WebElement child : children)
        {
            text = text.replaceFirst(child.getText(), "").trim();
        }
        return text;

    }

    /**
     * Return Text values of multiple element
     *
     * @return List<String> elements text values
     */
    public List<String> getTexts() {
        List<WebElement> elems = driver.findElements(locator);
        List<String> textValues = new ArrayList<String>();
        for (WebElement elem : elems) {
            textValues.add(elem.getText());
        }
        return textValues;
    }

    /**
     * Get Attribute value of element
     *
     * @return The attribute/property's current value or null if the value is not set.
     */
    public String getAttributeValue(String attributeName) {
        return driver.findElement(locator).getAttribute(attributeName);
    }

    /**
     * Find element
     *
     * @return The first matching {@link WebElement} on the current page
     */
    public WebElement findElement() {
        return driver.findElement(locator);
    }

    /**
     * Find list of elements
     *
     * @return A list of all {@link WebElement}, or an empty list if nothing matches
     */
    public List<WebElement> findElements() {
        return driver.findElements(locator);
    }

    /**
     * Get Css value of element
     *
     * @param propName
     * @return The current, computed value of the property.
     */
    public String getCssValue(String propName) {
        return driver.findElement(locator).getCssValue(propName);
    }

    /**
     * Check whether element is selected or not For check box and radio button e.t.c...
     *
     * @return True if the element is currently selected or checked, false otherwise.
     */
    public boolean isSelected() {
        boolean isSelected = false;
        System.out.println("Checked=" + driver.findElement(locator).getAttribute("checked"));
        System.out.println ("element =" + locator);
        if (driver.findElement(locator).isSelected() ||
                (driver.findElement(locator).getAttribute("checked") != null && driver.findElement(locator).getAttribute("Checked").equals("true"))) {
            isSelected = true;
        }

        return isSelected;

    }

    /**
     * Move to and highlight element for easier tracing Be careful, this method might break the
     * element
     *
     * @return {@link BaseLocator}
     * @throws InterruptedException
     */
    public BaseLocator hightlight() throws InterruptedException {
        // Not recording or debugging then does not need highlight
        if (!TestParams.SCREEN_RECORDING && !TestParams.DEBUGGING) {
            return this;
        }
        // Element not displayed then return
        try {
            if (!this.findElement().isDisplayed()) {
                return this;
            }

            // Change style
            String originalStyle = this.getAttributeValue("style");
            String newStyle = "background: yellow; border: 2px solid red;";
            // move to element before change style
            moveToElement();
            // change to new style
            JavascriptSupport.applyStyle(driver, this.findElement(), newStyle);
            Thread.sleep(300);
            // change back
            JavascriptSupport.applyStyle(driver, this.findElement(), originalStyle);
            return this;
        } catch (NoSuchElementException e) {
            // Element not existed then return
            return this;
        } catch (StaleElementReferenceException e) {
            // Stale element
            return this;
        }

    }

    /**
     * Clear text field by action
     *
     * @return {@link BaseLocator}
     * @throws InterruptedException
     */
    public BaseLocator clearTextFieldAction() throws InterruptedException {
        WebElement element = driver.findElement(locator);
        element.clear();
        Actions action = new Actions(driver);
        action.sendKeys(Keys.SPACE).sendKeys(Keys.BACK_SPACE).perform();
        Thread.sleep(1000);
        return this;
    }

    /**
     * Set value of text field by action
     *
     * @return {@link BaseLocator}
     * @throws InterruptedException
     */
    public BaseLocator setValuesAction(CharSequence value) {
//        Actions action = new Actions(driver);
//        System.out.println("xpath here: "+locator);
//        if (System.getProperty("os.name").startsWith("Mac")) {
//            action.moveToElement(driver.findElement(locator)).click()
//                    .sendKeys(Keys.chord(Keys.COMMAND, "a"), value).perform();
//        } else {
//            action.moveToElement(driver.findElement(locator)).click()
//                    .sendKeys(Keys.chord(Keys.LEFT_CONTROL, "a"), value).perform();
//            action.sendKeys(Keys.DELETE);
//        }
        WebElement element = driver.findElement(locator);
//        if (System.getProperty("os.name").startsWith("Mac")) {
//            action.moveToElement(driver.findElement(locator)).click()
//                    .sendKeys(Keys.chord(Keys.COMMAND, "a"), value).perform();
//        } else {
//            action.moveToElement(driver.findElement(locator)).click()
//                    .sendKeys(Keys.chord(Keys.LEFT_CONTROL, "a"), value).perform();
//            action.moveToElement(driver.findElement(locator)).click().sendKeys(Keys.DELETE);
//        }
        element.clear();
        element.click();
        element.sendKeys(Keys.chord(Keys.LEFT_CONTROL, "a"), value);
        return this;
    }
    public BaseLocator setBlankValue() {
        WebElement element = driver.findElement(locator);
        element.clear();
        element.click();
        element.sendKeys(Keys.chord(Keys.LEFT_CONTROL, "a",Keys.DELETE,""));
        return this;
    }

    /**
     * Send keys action
     *
     * @return {@link BaseLocator}
     * @throws InterruptedException
     */
    public BaseLocator sendKeysAction(CharSequence value) {
        Actions action = new Actions(driver);
        action.moveToElement(driver.findElement(locator)).click().sendKeys(value).perform();
        return this;
    }

    /**
     * Wait until number of elements present on web page
     *
     * @return {@link BaseLocator}
     */
    public BaseLocator waitUntilNumberOfElements(int num) {
        wait.until(CustomWait.waitUntilNumberOfElement(driver, locator, num));
        return this;
    }

    /**
     * Wait until number of elements present on web page
     *
     * @param timeout
     * @return {@link BaseLocator}
     */
    public BaseLocator waitUntilNumberOfElements(int num, int timeout) {
        new WebDriverWait(driver, timeout)
                .until(CustomWait.waitUntilNumberOfElement(driver, locator, num));
        return this;
    }

    public WebElement findChildElement(WebElement element) {
        return element.findElement(this.locator);
    }

    public BaseLocator waitUntilChildElementVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(element, this.locator));
        return this;
    }

    public BaseLocator hightlightChildElement(WebElement element) throws InterruptedException {
        // Not recording or debugging then does not need highlight
        if (!TestParams.SCREEN_RECORDING && !TestParams.DEBUGGING) {
            return this;
        }
        // Element not displayed then return
        try {
            if (!this.findChildElement(element).isDisplayed()) {
                return this;
            }

            // Change style
            String originalStyle = this.findChildElement(element).getAttribute("style");
            String newStyle = "background: yellow; border: 2px solid red;";
            // move to element before change style
            moveToElement(this.findChildElement(element));
            // change to new style
            JavascriptSupport.applyStyle(driver, this.findChildElement(element), newStyle);
            Thread.sleep(300);
            // change back
            JavascriptSupport.applyStyle(driver, this.findChildElement(element), originalStyle);
            return this;
        } catch (NoSuchElementException e) {
            // Element not existed then return
            return this;
        } catch (StaleElementReferenceException e) {
            // Stale element
            return this;
        }

    }

    public BaseLocator moveToRight(int dimension) {
        WebElement startElement = driver.findElement(this.locator);
            Actions builder = new Actions(driver);

            builder.clickAndHold(startElement).moveByOffset(dimension,0).release().build().perform();


        return this;
    }
    public BaseLocator moveToLeft(int dimension) {
        WebElement startElement = driver.findElement(this.locator);
        Actions builder = new Actions(driver);

        builder.clickAndHold(startElement).moveByOffset(dimension,0).release().build().perform();


        return this;
    }

    public Dimension getDimensions() {
        return driver.findElement(locator).getSize();
    }

    public Boolean isVisible(By locator) {
        List<WebElement> elements = driver.findElements(locator);
        if (elements.size() != 0) {
            // If threre's element
            try {
                // return is displayed or not
                return elements.get(0).isDisplayed();
            } catch (StaleElementReferenceException e) {
                // Try again if hit StaleElementReferenceException
                if (driver.findElements(locator).size() != 0) {
                    return driver.findElements(locator).get(0).isDisplayed();
                }
            }
        }
        return false;
    }

    @Override
    public List<WebElement> findElements(SearchContext context) {
        return null;
    }
}


