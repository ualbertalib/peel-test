package ca.ualibrary.dit.peel.stories;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.jbehave.core.annotations.AfterStory;
import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.BeforeStory;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.thoughtworks.selenium.SeleneseTestBase;

public class PeelSteps extends SeleneseTestBase {

	protected WebDriver driver;
  WebDriverWait wait;
	protected String baseUrl;
	private Properties prop;
  protected boolean isSample = false;
  protected boolean isProduction = true;
	private static String PEEL_SOLR_SAMPLE = "peel-solr";
	private static String PRODUCTION_SAMPLE = "production";
  private static String BROWSER_IEXPLORER = "iexplorer";
  private static String BROWSER_SAFARI = "safari";
  private static String BROWSER_CHROME = "chrome";
  protected static long WAIT_TIME = 5;

	public PeelSteps() {
		super();
	}

	@BeforeStory
	public void setUp() {
    prop = new Properties();
    
    
    try {
      prop.load(new FileInputStream(new java.io.File("").getAbsolutePath()
          + "/test.properties"));
      baseUrl = prop.getProperty("baseUrl");
      isSample = PEEL_SOLR_SAMPLE.equals(prop.getProperty("sample"));
      isProduction = PRODUCTION_SAMPLE.equals(prop.getProperty("sample"));
      
      initBrowserDriver(prop.getProperty("browser"));
      
    } catch (Exception e) {
      initBrowserDriver(System.getenv("browser"));
      baseUrl = System.getenv("baseUrl");
      if (null == baseUrl) baseUrl = "http://peeldev1.library.ualberta.ca";
      String sample = System.getenv("sample");
      if (null != sample) {
        isSample = PEEL_SOLR_SAMPLE.equals(sample);
        isProduction = PRODUCTION_SAMPLE.equals(sample);
      }
    }
    
    if (!baseUrl.endsWith("/")) baseUrl += "/";
    wait = new WebDriverWait(driver, WAIT_TIME);

	}

  private void initBrowserDriver(String property) {
    if (BROWSER_IEXPLORER.equals(property)) {
      File iedriver = new File("selenium/IEDriverServer.exe");
      System.setProperty("webdriver.ie.driver", iedriver.toString());
      
      driver = new InternetExplorerDriver();
    } else if (BROWSER_SAFARI.equals(property)) {
      driver = new SafariDriver();
    } else if (BROWSER_CHROME.equals(property)) {
      File chromedriver = new File("selenium/chromedriver_1.exe");
      System.setProperty("webdriver.chrome.driver", chromedriver.toString());
      driver = new ChromeDriver();
    } else { // firefox is the default
      driver = new FirefoxDriver();
    }
  }
  
  @AfterStory
	public void tearDown() {
		driver.quit();
	}

	@Given("visitor is on the front page")
	public void givenVisitorIsOnTheFrontPage() {
		driver.get(baseUrl + "index.html");
    wait.until(ExpectedConditions
        .titleIs("Peel's Prairie Provinces - Sources for Western Canada and Western Canadian History"));
		assertEquals(
				"Peel's Prairie Provinces - Sources for Western Canada and Western Canadian History",
				driver.getTitle());
	}

  @When("user clicks 'search'")
	public void whenUserClicksSearch() {
    WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By
        .id("submit")));
    element.click();
	}

	protected void enterInElement(By element, String value) {
		driver.findElement(element).clear();
		driver.findElement(element).sendKeys(value);
	}

	@When("user enters $query in the header form")
	@Alias("user enters <query> in the header form")
	public void whenUserEntersQueryInTheHeaderForm(@Named("query") String query) {
		enterInElement(By.id("keywords"), query);
	}

  @Then("title is $title")
  public void thenTitleIsTitle(@Named("title") String title) {
    wait.until(ExpectedConditions.titleIs(title));
    assertEquals(title, driver.getTitle());
  }
}