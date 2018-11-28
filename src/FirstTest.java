import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;

public class FirstTest {

  private AppiumDriver driver;

  @Before
  public void setUp() throws Exception {
    DesiredCapabilities capabilities = new DesiredCapabilities();

    capabilities.setCapability("platformName", "Android");
    capabilities.setCapability("deviceName", "AndroidTestDevice");
    capabilities.setCapability("platformVersion", "8.0");
    capabilities.setCapability("automationName", "Appium");
    capabilities.setCapability("appPackage", "org.wikipedia");
    capabilities.setCapability("appActivity", ".main.MainActivity");
    capabilities.setCapability("app", "C:\\MY_DATA\\QA\\JavaAppiumAutomation\\apks\\org.wikipedia.apk");

    driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
  }

  @After
  public void tearDown() {
    driver.quit();
  }

  @Test
  public void firstTest() {

    waitForElementAndClick(
            By.xpath("//*[contains(@text,'Search Wikipedia')]"),
            "Cannot Find 'Search Wikipedia' Input",
            5);

    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search…')]"),
            "Java",
            "Cannot Find Search Input",
            5);

    waitForElementPresent(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
            "Cannot Find 'Object-oriented programming language' topic searching by 'Java'",
            15
    );
  }

  @Test
  public void testCancelSearch() {
    waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot Find 'Search Wikipedia' Input",
            5
    );

    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search…')]"),
            "Java",
            "Cannot Find Search Input",
            5);

    waitForElementAndClear(
            By.id("org.wikipedia:id/search_src_text"),
            "Cannot Find Search Field",
            5
    );

    waitForElementAndClick(
            By.id("org.wikipedia:id/search_close_btn"),
            "Cannot Find 'X' icon to cancel search",
            5
    );

    waitForElementNotPresent(
            By.id("org.wikipedia:id/search_close_btn"),
            "'X' icon is still present on the page",
            5
    );

  }

  @Test
  public void testCancelSearchEx3() {
    waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot Find 'Search Wikipedia' Input",
            5
    );

    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search…')]"),
            "NBA",
            "Cannot Find Search Input",
            5);

    waitForElementPresent(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='NBA G League']"),
            "Cannot Find 'NBA G League' topic searching by 'NBA'",
            15
    );

    waitForElementPresent(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='NBA salary cap']"),
            "Cannot Find 'NBA salary cap' topic searching by 'NBA'",
            15
    );

    waitForElementAndClear(
            By.id("org.wikipedia:id/search_src_text"),
            "Cannot Find Search Field",
            5
    );

    waitForElementAndClick(
            By.id("org.wikipedia:id/search_close_btn"),
            "Cannot Find 'X' icon to cancel search",
            5
    );

    waitForElementNotPresent(
            By.id("org.wikipedia:id/search_close_btn"),
            "'X' icon is still present on the page",
            5
    );

  }

  @Test
  public void testCancelSearchEx4() {
    waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot Find 'Search Wikipedia' Input",
            5
    );

    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search…')]"),
            "Amazon",
            "Cannot Find Search Input",
            5);

    List<WebElement> searchResultsTitles = waitForElementsPresent(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@resource-id='org.wikipedia:id/page_list_item_title']"),
            "Cannot Find 'Amazon' search results titles",
            15
    );

    for (int i=0; i<searchResultsTitles.size(); i++) {
      String articleTitle = searchResultsTitles.get(i).getAttribute("text");
      System.out.println("Article Title: " + articleTitle);
      Assert.assertThat(articleTitle, containsString("Amazon"));
    }
  }

  @Test
  public void testCompareArticleTitles() {
    waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot Find 'Search Wikipedia' Input",
            5
    );

    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search…')]"),
            "Java",
            "Cannot Find Search Input",
            5);

    waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
            "Cannot Find 'Object-oriented programming language' topic searching by 'Java'",
            5
    );

    WebElement titleElement = waitForElementPresent(
            By.id("org.wikipedia:id/view_page_title_text"),
            "Cannot find article title",
            15
    );

    String articleTitle = titleElement.getAttribute("text");

    Assert.assertEquals(
            "We see unexpected title",
            "Java (programming language)",
            articleTitle
    );
  }

  @Test
  public void testSwipeArticle() {
    waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot Find 'Search Wikipedia' Input",
            5
    );

    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search…')]"),
            "Appium",
            "Cannot Find Search Input",
            5);

    waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
            "Cannot Find 'Appium' topic",
            5
    );

    waitForElementPresent(
            By.id("org.wikipedia:id/view_page_title_text"),
            "Cannot find article title",
            15
    );

     swipeUpToFindElement(
             By.xpath("//*[@text='View page in browser']"),
             "Cannot find the end of the article",
             20);


  }

  private WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds) {
    WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
    wait.withMessage(error_message + "\n");
    return wait.until(ExpectedConditions.presenceOfElementLocated(by));
  }

  private List<WebElement> waitForElementsPresent(By by, String error_message, long timeoutInSeconds) {
    WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
    wait.withMessage(error_message + "\n");
    return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
  }

  private WebElement waitForElementPresent(By by, String error_message) {
    return waitForElementPresent(by, error_message, 5);
  }

  private WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds) {
    WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
    element.click();
    return element;
  }

  private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds) {
    WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
    element.sendKeys(value);
    return element;
  }

  private boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds) {
    WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
    wait.withMessage(error_message + "\n");
    return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
  }

  private WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds) {
    WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
    element.clear();
    return element;
  }

  protected void swipeUp(int timeOfSwipe){
    TouchAction action = new TouchAction(driver);
    Dimension size = driver.manage().window().getSize();
    int x = size.width / 2;
    int start_y = (int) (size.height * 0.8);
    int end_y = (int) (size.height * 0.2);

    action.press(x, start_y).waitAction(timeOfSwipe).moveTo(x, end_y).release().perform();
  }

  protected void swipeUpQuick(){
    swipeUp(200);
  }

  protected void swipeUpToFindElement(By by, String error_message, int max_swipes){

    int already_swiped = 0;
    while(driver.findElements(by).size() == 0){
      if(already_swiped > max_swipes){
        waitForElementPresent(by, "Cannot find element by swiping up. \n" + error_message, 0);
        return;
      }
      swipeUpQuick();
      ++already_swiped;
    }

  }

}
