import io.appium.java_client.TouchAction;
import lib.CoreTestCase;
import lib.ui.MainPageObject;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import sun.applet.Main;

import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;

public class FirstTest extends CoreTestCase {

  private MainPageObject MainPageObject;

  protected void setUp()throws Exception{
    super.setUp();
    MainPageObject = new MainPageObject(driver);
  }

  @Test
  public void testSearch() {

    MainPageObject.waitForElementAndClick(
            By.xpath("//*[contains(@text,'Search Wikipedia')]"),
            "Cannot Find 'Search Wikipedia' Input",
            5);

    MainPageObject.waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search…')]"),
            "Java",
            "Cannot Find Search Input",
            5);

    MainPageObject.waitForElementPresent(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
            "Cannot Find 'Object-oriented programming language' topic searching by 'Java'",
            15
    );
  }

  @Test
  public void testCancelSearch() {
    MainPageObject.waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot Find 'Search Wikipedia' Input",
            5
    );

    MainPageObject.waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search…')]"),
            "Java",
            "Cannot Find Search Input",
            5);

    MainPageObject.waitForElementAndClear(
            By.id("org.wikipedia:id/search_src_text"),
            "Cannot Find Search Field",
            5
    );

    MainPageObject.waitForElementAndClick(
            By.id("org.wikipedia:id/search_close_btn"),
            "Cannot Find 'X' icon to cancel search",
            5
    );

    MainPageObject.waitForElementNotPresent(
            By.id("org.wikipedia:id/search_close_btn"),
            "'X' icon is still present on the page",
            5
    );

  }

  @Test
  public void testCancelSearchEx3() {
    MainPageObject.waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot Find 'Search Wikipedia' Input",
            5
    );

    MainPageObject.waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search…')]"),
            "NBA",
            "Cannot Find Search Input",
            5);

    MainPageObject.waitForElementPresent(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='NBA G League']"),
            "Cannot Find 'NBA G League' topic searching by 'NBA'",
            15
    );

    MainPageObject.waitForElementPresent(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='NBA salary cap']"),
            "Cannot Find 'NBA salary cap' topic searching by 'NBA'",
            15
    );

    MainPageObject.waitForElementAndClear(
            By.id("org.wikipedia:id/search_src_text"),
            "Cannot Find Search Field",
            5
    );

    MainPageObject.waitForElementAndClick(
            By.id("org.wikipedia:id/search_close_btn"),
            "Cannot Find 'X' icon to cancel search",
            5
    );

    MainPageObject.waitForElementNotPresent(
            By.id("org.wikipedia:id/search_close_btn"),
            "'X' icon is still present on the page",
            5
    );

  }

  @Test
  public void testCancelSearchEx4() {
    MainPageObject.waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot Find 'Search Wikipedia' Input",
            5
    );

    MainPageObject.waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search…')]"),
            "Amazon",
            "Cannot Find Search Input",
            5);

    List<WebElement> searchResultsTitles = MainPageObject.waitForElementsPresent(
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
    MainPageObject.waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot Find 'Search Wikipedia' Input",
            5
    );

    MainPageObject.waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search…')]"),
            "Java",
            "Cannot Find Search Input",
            5);

    MainPageObject.waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
            "Cannot Find 'Object-oriented programming language' topic searching by 'Java'",
            5
    );

    WebElement titleElement = MainPageObject.waitForElementPresent(
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
    MainPageObject.waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot Find 'Search Wikipedia' Input",
            5
    );

    MainPageObject.waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search…')]"),
            "Appium",
            "Cannot Find Search Input",
            5);

    MainPageObject.waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
            "Cannot Find 'Appium' topic",
            5
    );

    MainPageObject.waitForElementPresent(
            By.id("org.wikipedia:id/view_page_title_text"),
            "Cannot find article title",
            15
    );

    MainPageObject.swipeUpToFindElement(
             By.xpath("//*[@text='View page in browser']"),
             "Cannot find the end of the article",
             20);
  }

  @Test
  public void testSaveFirstArticleToMyList(){
    MainPageObject.waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot Find 'Search Wikipedia' Input",
            5
    );

    MainPageObject.waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search…')]"),
            "Java",
            "Cannot Find Search Input",
            5);

    MainPageObject.waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
            "Cannot Find 'Object-oriented programming language' topic searching by 'Java'",
            5
    );

    MainPageObject.waitForElementPresent(
            By.id("org.wikipedia:id/view_page_title_text"),
            "Cannot find article title",
            15
    );

    MainPageObject.waitForElementAndClick(
            By.xpath("//android.widget.ImageView[@content-desc='More options']"),
            "Cannot find button to open article options",
            5
    );

    MainPageObject.waitForElementAndClick(
            By.xpath("//*[@text='Add to reading list']"),
            "Cannot find option to add article to reading list",
            5
    );

    MainPageObject.waitForElementAndClick(
            By.id("org.wikipedia:id/onboarding_button"),
            "Cannot find 'Got It' tip overlay",
            5
    );

    MainPageObject.waitForElementAndClear(
            By.id("org.wikipedia:id/text_input"),
            "Cannot find input to set name of articles list",
            5
    );

    String name_of_folder = "Learning programming";

    MainPageObject.waitForElementAndSendKeys(
            By.id("org.wikipedia:id/text_input"),
            name_of_folder,
            "Cannot input text into articles list name field",
            5
    );

    MainPageObject.waitForElementAndClick(
            By.xpath("//*[@text='OK']"),
            "Cannot push 'OK' button",
            5
    );

    MainPageObject.waitForElementAndClick(
            By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
            "Cannot push 'X' icon to close an article",
            5
    );

    MainPageObject.waitForElementAndClick(
            By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
            "Cannot find My Lists button",
            5
    );

    MainPageObject.waitForElementAndClick(
            By.xpath("//*[@text='" + name_of_folder + "']"),
            "Cannot Find 'Learning Programming' articles list",
            5
    );

    MainPageObject.swipeElementToLeft(
            By.xpath("//*[@text='Java (programming language)']"),
            "Cannot find saved article in My list"
    );

    MainPageObject.waitForElementNotPresent(
            By.xpath("//*[@text='Java (programming language)']"),
            "Cannot delete saved article",
            5
    );
  }

  @Test
  public void testSaveTwoArticlesToMyListEx5(){
    MainPageObject.waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot Find 'Search Wikipedia' Input",
            5
    );

    String search_input = "Java";
    MainPageObject.waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search…')]"),
            search_input,
            "Cannot Find Search Input",
            5);

    MainPageObject.waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
            "Cannot Find 'Object-oriented programming language' topic searching by" + search_input,
            5
    );

    MainPageObject.waitForElementPresent(
            By.id("org.wikipedia:id/view_page_title_text"),
            "Cannot find article title",
            15
    );

    String first_article_title = MainPageObject.waitForElementAndGetAttribute(
            By.id("org.wikipedia:id/view_page_title_text"),
            "text",
            "Cannot find article title",
            15
    );

    MainPageObject.waitForElementAndClick(
            By.xpath("//android.widget.ImageView[@content-desc='More options']"),
            "Cannot find button to open article options",
            5
    );

    MainPageObject.waitForElementAndClick(
            By.xpath("//*[@text='Add to reading list']"),
            "Cannot find option to add article to reading list",
            5
    );

    MainPageObject.waitForElementAndClick(
            By.id("org.wikipedia:id/onboarding_button"),
            "Cannot find 'Got It' tip overlay",
            5
    );

    MainPageObject.waitForElementAndClear(
            By.id("org.wikipedia:id/text_input"),
            "Cannot find input to set name of articles list",
            5
    );

    String name_of_folder = "Learning programming";

    MainPageObject.waitForElementAndSendKeys(
            By.id("org.wikipedia:id/text_input"),
            name_of_folder,
            "Cannot input text into articles list name field",
            5
    );

    MainPageObject.waitForElementAndClick(
            By.xpath("//*[@text='OK']"),
            "Cannot push 'OK' button",
            5
    );

    MainPageObject.waitForElementAndClick(
            By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
            "Cannot push 'X' icon to close an article",
            5
    );

    MainPageObject.waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot Find 'Search Wikipedia' Input",
            5
    );

    MainPageObject.waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search…')]"),
            search_input,
            "Cannot Find Search Input",
            5);

    MainPageObject.waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Java version history']"),
            "Cannot Find 'Object-oriented programming language' topic searching by" + search_input,
            5
    );

    MainPageObject.waitForElementPresent(
            By.id("org.wikipedia:id/view_page_title_text"),
            "Cannot find article title",
            15
    );

    MainPageObject.waitForElementAndClick(
            By.xpath("//android.widget.ImageView[@content-desc='More options']"),
            "Cannot find button to open article options",
            5
    );

    MainPageObject.waitForElementAndClick(
            By.xpath("//*[@text='Add to reading list']"),
            "Cannot find option to add article to reading list",
            5
    );

    MainPageObject.waitForElementAndClick(
            By.xpath("//*[@text='" + name_of_folder + "']"),
            "Cannot find option to add article to reading list",
            5
    );

    MainPageObject.waitForElementAndClick(
            By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
            "Cannot push 'X' icon to close an article",
            5
    );

    MainPageObject.waitForElementAndClick(
            By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
            "Cannot find My Lists button",
            5
    );

    MainPageObject.waitForElementAndClick(
            By.xpath("//*[@text='" + name_of_folder + "']"),
            "Cannot Find 'Learning Programming' articles list",
            5
    );

    MainPageObject.swipeElementToLeft(
            By.xpath("//*[@text='Java version history']"),
            "Cannot find saved article in My list"
    );

    MainPageObject.waitForElementNotPresent(
            By.xpath("//*[@text='Java version history']"),
            "Cannot delete saved article",
            5
    );

    MainPageObject.waitForElementAndClick(
            By.xpath("//*[@text='" + first_article_title + "']"),
            "Cannot Find 'Learning Programming' articles list",
            5
    );

    String saved_article_title = MainPageObject.waitForElementAndGetAttribute(
            By.id("org.wikipedia:id/view_page_title_text"),
            "text",
            "Cannot find article title",
            15
    );

    Assert.assertEquals(
            "Saved article title does not match the expected title",
            first_article_title,
            saved_article_title
    );
  }

  @Test
  public void testAssertTitlePresentEx6() {
    MainPageObject.waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot Find 'Search Wikipedia' Input",
            5
    );

    MainPageObject.waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search…')]"),
            "Java",
            "Cannot Find Search Input",
            5);

    MainPageObject.waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
            "Cannot Find 'Object-oriented programming language' topic searching by 'Java'",
            5
    );

    MainPageObject.assertElementPresent(
            By.xpath("//*[@resource-id='org.wikipedia:id/view_page_title_text']"),
            "Article title is not found"
    );
  }

  @Test
  public void testAmountOfNotEmptySearch(){
    MainPageObject.waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot Find 'Search Wikipedia' Input",
            5
    );

    String search_line = "Linkin Park Discography";
    MainPageObject.waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search…')]"),
            search_line,
            "Cannot Find Search Input",
            5);

    String search_results_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
    MainPageObject.waitForElementPresent(
            By.xpath(search_results_locator),
            "Cannot find anything by search input: " + search_line,
            15
    );

    int number_of_search_results = MainPageObject.getAmountOfElements(By.xpath(search_results_locator));

    Assert.assertTrue(
            "We found too few results",
            number_of_search_results > 0
    );
  }

  @Test
  public void testAmountOfEmptySearch() {
    MainPageObject.waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot Find 'Search Wikipedia' Input",
            5
    );

    String search_line = "fghfghfghfghgfh";
    MainPageObject.waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search…')]"),
            search_line,
            "Cannot Find Search Input",
            5);

    String search_results_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
    String empty_result_label = "//*[@text='No results found']";
    MainPageObject.waitForElementPresent(
            By.xpath(empty_result_label),
            "Cannot find empty result label by search input: " + search_line,
            15
    );

    MainPageObject.assertElementNotPresent(
            By.xpath(search_results_locator),
            "We found some results by search input " + search_line
    );
  }

  @Test
  public void testChangeScreenOrientationOnSearchResults(){
    MainPageObject.waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot Find 'Search Wikipedia' Input",
            5
    );

    String search_line = "Java";
    MainPageObject.waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search…')]"),
            search_line,
            "Cannot Find Search Input",
            5);

    MainPageObject.waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
            "Cannot Find 'Object-oriented programming language' topic searching by" + search_line,
            15
    );

    String title_before_rotation = MainPageObject.waitForElementAndGetAttribute(
            By.id("org.wikipedia:id/view_page_title_text"),
            "text",
            "Cannot find title of the article",
            15
    );

    driver.rotate(ScreenOrientation.LANDSCAPE);

    String title_after_rotation = MainPageObject.waitForElementAndGetAttribute(
            By.id("org.wikipedia:id/view_page_title_text"),
            "text",
            "Cannot find title of the article",
            15
    );

    Assert.assertEquals(
            "Article title have been changed after screen rotation",
            title_before_rotation,
            title_after_rotation
    );

    driver.rotate(ScreenOrientation.PORTRAIT);

    String title_after_second_rotation = MainPageObject.waitForElementAndGetAttribute(
            By.id("org.wikipedia:id/view_page_title_text"),
            "text",
            "Cannot find title of the article",
            15
    );

    Assert.assertEquals(
            "Article title have been changed after screen rotation",
            title_before_rotation,
            title_after_second_rotation
    );
  }

  @Test
  public void testSearchArticleInBackground() {
    MainPageObject.waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot Find 'Search Wikipedia' Input",
            5
    );

    MainPageObject.waitForElementAndSendKeys(
            By.xpath("//*[contains(@text,'Search…')]"),
            "Java",
            "Cannot Find Search Input",
            5);

    MainPageObject.waitForElementPresent(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
            "Cannot Find 'Object-oriented programming language' topic searching by 'Java'",
            5
    );

    driver.runAppInBackground(5);

    MainPageObject.waitForElementPresent(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
            "Cannot Find 'Object-oriented programming language' topic after returning from background",
            5
    );
  }
}
