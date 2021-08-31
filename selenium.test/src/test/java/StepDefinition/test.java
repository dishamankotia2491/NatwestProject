package StepDefinition;

import static org.junit.Assert.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.cucumber.java.en.*;

public class test {

	WebDriver driver = null;
	String OrderId;
	
	@Given("Login to the site with {string} and {string}")
	public void Login(String username, String password) {
		String projPath = System.getProperty("user.dir");
		System.setProperty("webdriver.chrome.driver",projPath+"/src/test/resources/Drivers/chromedriver");
		
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
		driver.navigate().to("http://automationpractice.com/");
		driver.findElement(By.className("login")).click();
		driver.findElement(By.id("email")).sendKeys(username);
		driver.findElement(By.name("passwd")).sendKeys(password);
		driver.findElement(By.id("SubmitLogin")).click();
	}
	
	@When("I order Tshirt")
	public void OrderTshirt() {
		driver.findElement(By.id("search_query_top")).sendKeys("Faded Short Sleeve T-shirts");
		driver.findElement(By.name("submit_search")).click();
		if(driver.findElement(By.xpath("//img[@title='Faded Short Sleeve T-shirts']")).isDisplayed())
		{
			driver.findElement(By.xpath("//a[@title='Add to cart']"));
			String addCartSuccess = driver.findElement(By.xpath("//*[@id=\"layer_cart\"]/div[1]/div[1]/h2/text()")).getText();
			System.out.println(addCartSuccess);
			assertEquals("Product successfully added to your shopping cart", addCartSuccess);
			driver.findElement(By.xpath("//a[@title='Proceed to checkout']")).click();
			driver.findElement(By.xpath("//a[@title='Proceed to checkout']")).click();
			driver.findElement(By.name("processAddress")).click();
			driver.findElement(By.name("processCarrier")).click();
			driver.findElement(By.cssSelector("input#cgv")).click();
			driver.findElement(By.xpath("//*[@id=\"HOOK_PAYMENT\"]/div[1]/div/p/a")).click();
			driver.findElement(By.xpath("//*[@id=\"cart_navigation\"]/button/")).click();
			String OrderCompleteMsg = driver.findElement(By.xpath("//*[@id=\"center_column\"]/div/p/strong")).getText();
			System.out.println(OrderCompleteMsg);
			assertEquals("Your order on My Store is complete.", OrderCompleteMsg);
			String OrderIdMsg = driver.findElement(By.xpath("//*[@id=\"center_column\"]/div/text()[6]")).getText();
			String[] words=OrderIdMsg.split("\\s");
			OrderId = words[8];
			System.out.println(OrderId);
			
		}
		//driver.findElement(By.linkText("T-shirts")).click();
	}

	@Then("I am able to see the Tshirt in Order History")
	public void CheckOrderHistory() {
		
		driver.findElement(By.className("account")).click();
		driver.findElement(By.linkText("http://automationpractice.com/index.php?controller=history")).click();
		
		WebElement table = driver.findElement(By.className("w3-table-all"));
	    List<WebElement> allrows = table.findElements(By.tagName("tr"));
	    List<WebElement> allcols = table.findElements(By.tagName("td"));
	    
	    for(WebElement row: allrows){
	        List<WebElement> Cells = row.findElements(By.tagName("td"));
	        for(WebElement Cell:Cells){
	            if (Cell.getText().contains(OrderId))
	                System.out.println("Order is shown in order history");
	        }
	    }
		driver.close();
		driver.quit();
	}

	@When("I update the First Name")
	public void UpdateProfile() {
		
		driver.findElement(By.className("account")).click();
		driver.findElement(By.linkText("http://automationpractice.com/index.php?controller=identity")).click();
		driver.findElement(By.id("firstname")).sendKeys("Disha");
		driver.findElement(By.name("old_passwd")).sendKeys("Selenium123.");
		driver.findElement(By.name("submitIdentity")).click();
		
	}

	@Then("My Name should be updated successfully")
	public void ValidateProfileUpdate() {
		
		String ProfileUpdateMsg = driver.findElement(By.className("alert alert-success")).getText();
		System.out.println(ProfileUpdateMsg);
		assertEquals("Your personal information has been successfully updated.", ProfileUpdateMsg);
		
		driver.close();
		driver.quit();
		
	}


}
