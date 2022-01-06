package lib.ui;
import io.qameta.allure.Step;
import lib.Platform;
import io.appium.java_client.AppiumDriver;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.List;

abstract public class SearchPageObject extends MainPageObject{

   protected static String
            SEARCH_INIT_ELEMENT,
            SEARCH_INPUT,
            SEARCH_CANCEL_BUTTON,
            SEARCH_RESULT_BY_SUBSTRING_TPL,
            SEARCH_RESULT_ELEMENT,
            SEARCH_EMPTY_RESULT_ELEMENT,
            SEARCH_RESULT_TITLE_AND_DESCRIPTION_BY_SUBSTRINGS_TPL,
            ARTICLE_TITLE,
            RESULT_LIST,
            ARTICLE_TPL,
            CANCEL_SEARCH_BUTTON;


    public SearchPageObject(RemoteWebDriver driver){
        super(driver);
    }

    /*TEMPLATE METHODS*/
    private static String getResultSearchElement(String substring){
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }

    private static String getResultTitleAndDescription(String substring1, String substring2){
        return SEARCH_RESULT_TITLE_AND_DESCRIPTION_BY_SUBSTRINGS_TPL.replace("{SUBSTRING1}", substring1).replace("{SUBSTRING2}", substring2);
    }

    private static String getArticleXPath(String i){
        return ARTICLE_TPL.replace("{i}", i);
    }
    /*TEMPLATE METHODS*/

    @Step("Initialize the search field")
    public void initSearchInput(){
        this.waitForElementPresent(SEARCH_INIT_ELEMENT, "Cannot find search input after clicking search init element");
        this.waitForElementAndClick(SEARCH_INIT_ELEMENT, "Cannot find and click search init element", 5);
    }

    @Step("Waiting for button to cancel search result")
    public void waitForCancelButtonToAppear(){
        this.waitForElementPresent(SEARCH_CANCEL_BUTTON, "Cannot find search cancel button", 5);
    }

    @Step("Waiting for search cancel button to disappear")
    public void waitForCancelButtonToDisappear(){
        this.waitForElementNotPresent(SEARCH_CANCEL_BUTTON, "Search cancel button is still presented", 5);
    }

    @Step("Clicking button to cancel search result")
    public void clickCancelSearch(){
        this.waitForElementAndClick(SEARCH_CANCEL_BUTTON, "Cannot find and click search cancel button", 5);
    }

    @Step("Typing '{search_line}' to the search line")
    public void typeSearchLine(String search_line){
        this.waitForElementAndSendKeys(SEARCH_INPUT, search_line, "Cannot find and type into search input", 5);
    }

    @Step("Waiting for search result")
    public void waitForSearchResult(String substring){
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(search_result_xpath, "Cannot find search result with subscring " + substring, 5);
    }

    @Step("Waiting for search result and select an article by substring in article title")
    public void clickByArticleWithSubstring(String substring){
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(search_result_xpath, "Cannot find and click search result with subsctring " + substring, 5);
    }

    @Step("Getting amount of found articles")
    public int getAmountOfFoundArticles(){
        this.waitForElementPresent(
                SEARCH_RESULT_ELEMENT,
                "Cannot find anything by the request",
                15
        );
        return this.getAmountsOfElements(SEARCH_RESULT_ELEMENT);
    }

    @Step("Waiting for empty result label")
    public void waitForEmptyResultLabel(){
        this.waitForElementPresent(
                SEARCH_EMPTY_RESULT_ELEMENT,
                "Cannot find empty result element",
                15
        );
    }

    @Step("Making sure there are no results fo the search")
    public void assertThereIsNoResultOfSearch(){
        this.assertElementNotPresent(
                SEARCH_RESULT_ELEMENT,
                "We've found some results by request"
        );

    }

    @Step("Waiting the article by title and description")
    public void waitForElementByTitleAndDescription(String title, String description){
        this.waitForElementPresent(
                getResultTitleAndDescription(title, description),
                "Cannot find article with title " + title + " and description " + description,
                15
        );
    }

    @Step("Waiting for the cancel button and click")
    public void waitForCancelButtonAndClick(){
        this.waitForElementAndClick(
          CANCEL_SEARCH_BUTTON,
          "Cannot find search button",
          15
        );
    }

    @Step("Get the search result list")
    public WebElement getSearchResultList(){
        return this.waitForElementPresent(
          RESULT_LIST,
          "No results",
          15
        );
    }

    @Step("Get the list of articles")
    public List getArticlesTitlesList(){
        if (Platform.getInstance().isAndroid()) {
            return getSearchResultList().findElements(By.id("org.wikipedia:id/page_list_item_title"));
        } else if (Platform.getInstance().isIOS()){
            return getSearchResultList().findElements(By.xpath("//XCUIElementTypeCell/XCUIElementTypeOther/XCUIElementTypeStaticText[1]"));
        } else {
            return getSearchResultList().findElements(By.cssSelector("li"));
        }
    }


}
