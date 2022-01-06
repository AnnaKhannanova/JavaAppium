package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

@Epic("Tests for searching")
public class SearchTests extends CoreTestCase {

    @Test
    @Feature(value="Search")
    @DisplayName("Search test")
    @Description("Checking the search")
    @Severity(value = SeverityLevel.BLOCKER)
    public void testSearch(){

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("Object-oriented programming language");
    }

    @Test
    @Feature("Search")
    @DisplayName("Search cancel")
    @Description("Finding  the articles and canceling the search")
    @Severity(value = SeverityLevel.CRITICAL)
    public void testCancelSearch(){

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();

        if (Platform.getInstance().isIOS()){
            SearchPageObject.waitForCancelButtonAndClick();
        } else {
            SearchPageObject.waitForCancelButtonToAppear();
            SearchPageObject.clickCancelSearch();
        }

        SearchPageObject.waitForCancelButtonToDisappear();
    }


    @Test
    @Feature(value = "Search")
    @DisplayName("Not empty search amount")
    @Description("The relevant search returns a few articles")
    @Severity(value = SeverityLevel.NORMAL)
    public void testAmountOfNotEmptySearch(){

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        String search_line = "Linkin Park Diskography";
        SearchPageObject.typeSearchLine(search_line);

        int amount_of_search_results = SearchPageObject.getAmountOfFoundArticles();

        Assert.assertTrue(
                "We found too few results!",
                amount_of_search_results > 0
        );

    }

    @Test
    @Feature(value = "Search")
    @DisplayName("Empty search")
    @Description("The not relevant search returns null result")
    @Severity(value= SeverityLevel.NORMAL)
    public void testAmountOfEmptySearch(){

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        String search_line = "ddsfdsfd";
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.waitForEmptyResultLabel();
        SearchPageObject.assertThereIsNoResultOfSearch();

    }


}
