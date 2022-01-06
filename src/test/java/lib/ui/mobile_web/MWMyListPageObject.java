package lib.ui.mobile_web;

import lib.ui.MyListPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWMyListPageObject extends MyListPageObject {

    static {
        ARTICLE_BY_TITLE_TPL = "xpath://ul[contains(@class,'page-summary-list')]//h3[contains(text(), '{TITLE}')]";
        REMOVE_FROM_SAVED_BUTTON = "xpath://ul[contains(@class,'page-summary-list')]//h3[contains(text(), '{TITLE}')]/../../a[contains(@class,'watched')]";
        //"xpath://li[title='{TITLE}']/a[2]";//"xpath://ul[contains(@class,'page-summary-list')]//h3[contains(text(), '{TITLE}')]/../../div[contains(@class,'watched')]";
    }

    public MWMyListPageObject(RemoteWebDriver driver){

        super(driver);
    }
}
