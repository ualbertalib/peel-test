package ca.ualibrary.dit.peel.stories;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.jbehave.core.annotations.AfterStory;
import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.BeforeStory;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.thoughtworks.selenium.SeleneseTestBase;

public class PeelSteps extends SeleneseTestBase {

	protected WebDriver driver;
	protected String baseUrl;
	private Properties prop;
	protected boolean isSample;
	protected boolean isProduction;
	private static String PEEL_SOLR_SAMPLE = "peel-solr";
	private static String PRODUCTION_SAMPLE = "production";
  private static String BROWSER_IEXPLORER = "iexplorer";
	protected static long WAIT_TIME = 30;

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
      baseUrl = System.getenv("baseUrl");
      if (null == baseUrl) baseUrl = "http://peel.library.ualberta.ca";
    }
    
    driver.manage().timeouts().implicitlyWait(WAIT_TIME, TimeUnit.SECONDS);

	}

  private void initBrowserDriver(String property) {
    if (BROWSER_IEXPLORER.equals(property)) {
      File iedriver = new File("selenium/IEDriverServer.exe");
      System.setProperty("webdriver.ie.driver", iedriver.toString());
      
      driver = new InternetExplorerDriver();
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
    driver.manage().timeouts().pageLoadTimeout(WAIT_TIME, TimeUnit.SECONDS);
		assertEquals(
				"Peel's Prairie Provinces - Sources for Western Canada and Western Canadian History",
				driver.getTitle());
	}

	@When("user clicks 'search'")
	public void whenUserClicksSearch() {
		driver.findElement(By.id("submit")).click();
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

}