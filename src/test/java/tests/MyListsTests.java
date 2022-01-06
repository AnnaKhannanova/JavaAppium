package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

@Epic("Tests for reading lists")
public class MyListsTests extends CoreTestCase {

    private static final String name_of_folder = "Learning programming";
    private static final String
            login = "Chibrina",
            password = "chibrina1945";

    @Test
    @Features(value = {@Feature(value="Search"),@Feature(value="Article"),@Feature(value="List")})
    @DisplayName("Save an article to list")
    @Description("Save an article to the new reading list ")
    @Severity(value = SeverityLevel.NORMAL)
    public void testSaveFirstArticleToMyList() throws InterruptedException {

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        //Enter a search value
        SearchPageObject.typeSearchLine("Java");
        //Click the article
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        String article_title = ArticlePageObject.getArticleTitle();

        //Save the article to the list
        if (Platform.getInstance().isAndroid()){
            ArticlePageObject.addArticleToNewList(name_of_folder);
        } else{
            ArticlePageObject.addArticleToMySaved();
        }

        //Authorize in mobile version
        if (Platform.getInstance().isMw()){
            AuthorizationPageObject Auth = new AuthorizationPageObject(driver);
            //Click the 'Sign in' button
            Auth.clickAuthButton();
            //Enter login and password
            Auth.enterLoginData(login, password);
            //Confirm login and password
            Auth.submitForm();

            ArticlePageObject.waitForTitleElement();

            Assert.assertEquals("We are not on the same page after login.",
                    article_title,
                    ArticlePageObject.getArticleTitle()
            );
        }

        ArticlePageObject.closeArticle();
        if (Platform.getInstance().isIOS()){
            SearchPageObject.waitForCancelButtonAndClick();
        }

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        //Open the navigation menu
        NavigationUI.openNavigation();
        //Open the list
        NavigationUI.clickMyList();

        MyListPageObject MyListPageObject = MyListPageObjectFactory.get(driver);

        if (Platform.getInstance().isAndroid()){
            MyListPageObject.openFolderByName(name_of_folder);
        } else if (Platform.getInstance().isIOS()){
            MyListPageObject.waitAndClickCloseSyncButton();
        }

        MyListPageObject.swipeByArticleToDelete(article_title);

    }

}
