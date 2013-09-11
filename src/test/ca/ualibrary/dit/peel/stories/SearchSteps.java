package ca.ualibrary.dit.peel.stories;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.Aliases;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.pagefactory.ByChained;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;


public class SearchSteps extends PeelSteps {

	@Given("visitor is on the 'find books' page")
	public void givenVisitorIsOnTheFindBooksPage() {
		driver.get(baseUrl + "index.html");
		wait.until(ExpectedConditions.titleIs("Peel's Prairie Provinces - Sources for Western Canada and Western Canadian History"));
		assertEquals(
				"Peel's Prairie Provinces - Sources for Western Canada and Western Canadian History",
				driver.getTitle());
		driver.findElement(By.cssSelector("a.tab4")).click();
    wait.until(ExpectedConditions.titleIs("Advanced Search"));
		assertEquals("Advanced Search", driver.getTitle());
	}

	@Given("visitor is on the 'find newspapers advanced search' page")
	public void givenVisitorIsOnTheFindNewspapersAdvancedSearchPage() {
		driver.get(baseUrl + "/index.html");
    wait.until(ExpectedConditions
        .titleIs("Peel's Prairie Provinces - Sources for Western Canada and Western Canadian History"));
		assertEquals(
				"Peel's Prairie Provinces - Sources for Western Canada and Western Canadian History",
				driver.getTitle());
		driver.findElement(By.cssSelector("a.tab5")).click();
    wait.until(ExpectedConditions.titleIs("Newspapers"));
		assertEquals("Newspapers", driver.getTitle());
		driver.findElement(By.cssSelector("a.advanced"))
				.click();
    wait.until(ExpectedConditions.titleIs("Newspapers"));
		assertEquals("Newspapers", driver.getTitle());
	}

	@When("user enters <id> <value> in the form")
	@Alias("user enters $value in the form id $id")
	public void whenUserEntersIdValueInTheForm(@Named("id") String id,
			@Named("value") String value) {
		if (isOldGUI)
			id += "display";
		enterInElement(By.id(id), value);
	}

	@When("user selects fulltext")
	public void whenUserSelectsFulltext() {
		driver.findElement(By.id("status-dig")).click();
	}

	@When("user enters <name> <value> in the form")
	@Alias("user enters $value in the form name $name")
	public void whenUserEntersNameValueInTheForm(@Named("name") String name,
			@Named("value") String value) {
		enterInElement(By.name(name), value);
	}

	@When("user selects <name> <value> in the form")
	@Alias("user selects $value in the form name $name")
	public void whenUserSelectsNameValueInTheForm(@Named("name") String name,
			@Named("value") String value) {
		new Select(driver.findElement(By.name(name))).selectByValue(value);
	}

	@When("user enters <query> in the form")
	public void whenUserEntersQueryInTheForm(
			@Named("query") String query) {
		enterInElement(By.id("keywords2"), query);
	}

	@When("user clicks 'go'")
	public void whenUserClicksGo() {
		driver.findElement(By.id("submit2")).click();
	}

	@When("peelbib user selects <sort>")
	public void whenPeelbibUserSelectsSort(@Named("sort") String sort) {
		// TODO why do I have to control status-any (or something else) before
		// setting the sort?
		driver.findElement(By.id("status-any")).click();

		driver.findElement(By.id(sort)).click();
	}
	
	@When("user selects <sort>")
	public void whenUserSelectsSort(@Named("sort") String sort) {
		driver.findElement(By.id(sort)).click();
	}

	@When("user clicks 'submit' at the bottom of form")
	public void whenUserClicksSubmitAtTheBottomOfForm() {
		driver.findElement(By.cssSelector("p.buttons > input.submit")).click();
	}

	@When("user checks id $type")
	public void whenUserChecksIdType(@Named("type") String type) {
		driver.findElement(By.id(type)).click();
	}

	@When("user checks name $size")
	public void whenUserChecksNameSize(@Named("size") String size) {
		driver.findElement(By.name(size)).click();
	}

	@Then("breadcrumbs contain <query>")
	@Aliases(values = { "breadcrumbs contain <keywords>",
	"breadcrumbs contain $query" })
	public void thenBreadcrumbsContainQuery(@Named("query") String query) {
		if (isSample) {
			String breadcrumbsFoundExpected = "^[\\s\\S]*Query:.*"
					+ Pattern.quote(query) + ".*[\\s\\S]*$";
			String breadcrumbsFoundActual = driver.findElement(
					By.className("breadcrumbs")).getText();
			assertTrue("'" + breadcrumbsFoundActual + "' should match regex '"
					+ breadcrumbsFoundExpected + "'",
					breadcrumbsFoundActual.matches(breadcrumbsFoundExpected));
		}
	}

	@Then("hits <hits>")
	@Alias("hits $hits")
	public void thenHitsEquals(@Named("hits") String hits) {
    wait.until(ExpectedConditions.refreshed(ExpectedConditions
        .textToBePresentInElement(By.className("hits-found"), " hits found.")));
		String hitsFoundActual = driver.findElement(
				By.className("hits-found")).getText();
		int numHits = Integer.parseInt(hitsFoundActual.split(" ")[0]);
    if (Integer.parseInt(hits) > 0) {
      assertTrue("hits should be greater than zero", 0 < numHits);
    }
		if (isSample) {
			String hitsFoundExpected = hits + " hits found\\.";
			assertTrue("'" + hitsFoundActual + "' should match '"
					+ hitsFoundExpected + "'",
					hitsFoundActual.matches(hitsFoundExpected));
		}
	}

	@Then("first result is <peelbib>")
	@Alias("first result is $peelbib")
	public void thenFirstResultIsPeelbib(@Named("peelbib") String peelbib) {
		if (isSample) {
			String firstResultExpected = peelbib;
			String firstResultActual = driver.findElement(
					new ByChained(By.xpath("//li[@class='result'][@value=1]"),
							By.xpath("//dt/a"))).getText();
			assertTrue("'" + firstResultActual + "' should match '"
					+ firstResultExpected + "'",
					firstResultActual.matches(firstResultExpected));
		}
	}

  @Then("first newspaper result is <newstitle>")
  public void thenFirstNewspaperResultIsTitle(@Named("newstitle") String title) {
		if (isSample && !("Linux".equals(System.getProperty("os.name")) && driver instanceof ChromeDriver)) {
			String firstResultExpected = title;
			String firstResultActual = driver.findElement(
					By.xpath("//li[@class='result'][@value=1]/dl/dt"))
					.getText();
			assertTrue("'" + firstResultActual + "' should match '"
					+ firstResultExpected + "'",
					firstResultActual.contains(firstResultExpected));
		}
	}

	@Then("results are sorted by $sort")
	@Alias("results are sorted by <sort>")
	public void thenResultsAreSortedBySort(@Named("sort") String sortString)
			throws ParseException {
		WebElement firstResult = driver.findElement(By
				.xpath("//li[@class='result'][@value=1]"));
		WebElement secondResult = driver.findElement(By
				.xpath("//li[@class='result'][@value=2]"));
		Sorts sort = Sorts.valueOf(sortString.toLowerCase()
				.replace("sort-", "").replace("sort_", "")
				.replace("-", "_"));
		switch (sort) {
		case peelnum:
			assertPeelnumOrder(firstResult, secondResult);
			break;
		case pubyear_asc:
			assertPubyearOrder(firstResult, secondResult);
			break;
		case pubyear_desc:
			assertPubyearOrder(secondResult, firstResult);
			break;
		case author_asc:
			assertAuthorOrder(firstResult, secondResult);
			break;
		case author_desc:
			assertAuthorOrder(secondResult, firstResult);
			break;
		case title_asc:
			assertTitleOrder(firstResult, secondResult);
			break;
		case title_desc:
			assertTitleOrder(secondResult, firstResult);
			break;
		case date_asc:
			assertDateOrder(firstResult, secondResult);
			break;
		case date_desc:
			assertDateOrder(secondResult, firstResult);
			break;
		case score:
			// TODO: can't be tested?
		default:
			break;
		}
	}

	private void assertDateOrder(WebElement firstResult, WebElement secondResult)
			throws ParseException {

		String[] split = firstResult.findElement(By.xpath("dl/dt")).getText()
				.split(", ");
		split = secondResult.findElement(By.xpath("dl/dt")).getText()
				.split(", ");
		Date date1;
		Date date2;
		SimpleDateFormat format;
		if("Linux".equals(System.getProperty("os.name")) && driver instanceof ChromeDriver && !driver.getCurrentUrl().contains("locale")) {
			format = new SimpleDateFormat("dd MMMMM yyyy");
			date1 = format.parse(split[1]);
			date2 = format.parse(split[1]);
		} else {
			format = new SimpleDateFormat("MMMMM dd, yyyy");
			date1 = format.parse(split[1] + ", " + split[2]);
			date2 = format.parse(split[1] + ", " + split[2]);
		}
		assertTrue(date1.toString() + " should be before " + date2.toString(),
				date1.compareTo(date2) <= 0);
	}

	private void assertTitleOrder(WebElement firstResult,
			WebElement secondResult) {
		String title1 = firstResult.findElement(By.xpath("dl/dt")).getText()
				.replaceFirst("^.*[0-9]:", "").replaceFirst("(The|A)", "");
		String title2 = secondResult.findElement(By.xpath("dl/dt")).getText()
				.replaceFirst("^.*[0-9]:", "").replaceFirst("(The|A)", "");
		assertTrue(title1 + " should be before " + title2,
				title1.compareTo(title2) <= 0);
	}

	private void assertAuthorOrder(WebElement firstResult,
			WebElement secondResult) {
		String author1 = firstResult
				.findElement(By.xpath("dl/dd[@class='by']")).getText();
		author1 = author1.substring(author1.lastIndexOf("Author"),
				author1.indexOf("."));
		String author2 = secondResult.findElement(
				By.xpath("dl/dd[@class='by']")).getText();
		author2 = author2.substring(author2.lastIndexOf("Author"),
				author2.indexOf("."));
		assertTrue(author1 + " should be before " + author2,
				author1.compareTo(author2) <= 0);
	}

	private void assertPubyearOrder(WebElement firstResult,
			WebElement secondResult) {
		String pubyear1 = firstResult
				.findElement(By.xpath("dl/dd[@class='info']")).getText().trim();
		int sample = pubyear1.length() < 6 ? pubyear1.length() : 6;
		pubyear1 = pubyear1.substring(pubyear1.length() - sample).replaceAll(
				"[^0-9]*", "");
		String pubyear2 = secondResult
				.findElement(By.xpath("dl/dd[@class='info']")).getText().trim();
		sample = pubyear2.length() < 6 ? pubyear2.length() : 6;
		pubyear2 = pubyear2.substring(pubyear2.length() - sample).replaceAll(
				"[^0-9]*", "");
		if (!"".equals(pubyear1) && !"".equals(pubyear2))
			assertTrue(pubyear1 + " should be before " + pubyear2,
					pubyear1.compareTo(pubyear2) <= 0);
	}

	private void assertPeelnumOrder(WebElement firstResult,
			WebElement secondResult) {
		String peelnum1 = padPeelNum(firstResult.findElement(By.xpath("dl/dt/a"))
				.getText());
		String peelnum2 = padPeelNum(secondResult.findElement(By.xpath("dl/dt/a"))
				.getText());
		assertTrue(peelnum1 + " should be before " + peelnum2,
				peelnum1.compareTo(peelnum2) < 0);
	}

	private String padPeelNum(String text) {
    text = text.replaceAll("Peel ", "");
    int length = text.indexOf('.') > 0 ? text.indexOf('.') : text.length();
    for( int i = 0; i < 6-length; i++ )
      text = "0" + text;
    return text;
  }

  public enum Sorts {
		score,
		peelnum,
		pubyear_asc, 
		pubyear_desc, 
		author_asc, 
		author_desc, 
		title_asc, 
		title_desc,
		date_asc,
		date_desc
	}
}
