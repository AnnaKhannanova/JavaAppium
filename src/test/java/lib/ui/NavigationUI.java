package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;
import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class NavigationUI extends MainPageObject {

    protected static String
        MY_LIST_LINK,
        OPEN_NAVIGATION;

    public NavigationUI(RemoteWebDriver driver){
        super(driver);
    }

    @Step("Open the list of the article")
    public void clickMyList(){
        if (Platform.getInstance().isMw()){
            this.tryClickElementWithFewAttempts(
                    MY_LIST_LINK,
                    "Cannot find navigation button to my list",
                    5
            );
        } else {
            this.waitForElementAndClick(
                    MY_LIST_LINK,
                    "Cannot find navigation button to My list",
                    5
            );
        }
    }


    @Step("Open the navigation menu in mobile web")
    public void openNavigation() throws InterruptedException {
        if (Platform.getInstance().isMw()){
            Thread.sleep(2000);
            this.waitForElementAndClick(
                    OPEN_NAVIGATION,
                    "Cannot find and click navigation button.",
                    5
                    );
        } else {
            System.out.println("Method openNavigation() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }
}
