package stepDefinations;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import utils.DriverFactory;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;




public class FlipcartStepDefs {
	
	
		WebDriver driver;
	    WebDriverWait wait;
	    Map<String, Integer> cartProducts = new HashMap<>();


	    @Before
	    @Given("User open the Flipkart website")
	    public void user_open_the_flipkart_website() {
	    	driver = DriverFactory.initializeDriver();
	    	driver.manage().window().maximize();
	    	 wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	        driver.get("https://www.flipkart.com");
	    }
	    
	    @When("User search for {string}")
	    public void user_search_for(String searchTerm) {
	    	 WebElement searchBox = driver.findElement(By.name("q"));
		        searchBox.sendKeys(searchTerm);
		        searchBox.sendKeys(Keys.ENTER);

	    }
	    @When("User sort the results by {string}")
	    public void user_sort_the_results_by(String sortOption) {
	    	WebElement sortoption = driver.findElement(By.xpath("//div[text()='Price -- Low to High']"));
	    	sortoption.click();
	    	

	        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='Price -- Low to High']")));
	    }
	    @Then("User verify that prices are sorted in ascending order up to page {int}")
	    public void user_verify_that_prices_are_sorted_in_ascending_order_up_to_page(int pageLimit) {
	    	int pageLimit1 = 2;
	        List<Integer> allPrices = new ArrayList<>();
	        
	        for (int page = 1; page <= pageLimit1; page++) {
	            try {
	                Thread.sleep(3000);
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
   
	            List<WebElement> priceElements = driver.findElements(By.xpath("//div[@class='Nx9bqj']"));
	            for (WebElement priceElement : priceElements) {
	                String priceText = priceElement.getText().replace("₹", "").replace(",", "");
	                allPrices.add(Integer.parseInt(priceText));
	              
	            }
	            System.out.println(allPrices);
 
	            if (page < pageLimit1) {
	                WebElement nextButton = driver.findElement(By.xpath("//*[@class='_9QVEpD']/*"));
	                nextButton.click();
	            }
	        }
	    }
	    
	 
	    @When("User click on the second available product in the list and add it to the cart")
		public void user_click_on_the_second_available_product_in_the_list_and_add_it_to_the_cart() throws InterruptedException {
	    	 wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    	Thread.sleep(5000);
	    	driver.findElement(By.xpath("(//div[@class='Nx9bqj'])[2]")).click();
	    	ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
	        driver.switchTo().window(tabs.get(1));
	        Thread.sleep(5000);
	    	driver.findElement(By.xpath("//*[text()='Add to cart']")).click();
	    	driver.switchTo().window(tabs.get(0));
	    	   	
		   
		}

		@When("User click on the third available product in the list and add it to the cart")
		public void user_click_on_the_third_available_product_in_the_list_and_add_it_to_the_cart() throws InterruptedException {
			
	        
	        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	       Thread.sleep(5000);
			driver.findElement(By.xpath("(//div[@class='hCKiGj'])[3]")).click();
			ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
	        driver.switchTo().window(tabs.get(2));
	        
	    	driver.findElement(By.xpath("//*[contains(text(),' to cart')]")).click();
		}

		@When("User validate the correct products are added with the correct price")
		public void user_validate_the_correct_products_are_added_with_the_correct_price() {
			
			 
			    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Price details']"))); // Wait for cart to load

			    for (Map.Entry<String, Integer> entry : cartProducts.entrySet()) {
			        String productName = entry.getKey();
			        int expectedPrice = entry.getValue();

			        WebElement productElement = driver.findElement(By.xpath("//a[contains(text(),'" + productName + "')]"));
			        WebElement priceElement = productElement.findElement(By.xpath("//span[contains(@class,'LAlF6k re6bBo')]"));

			        int actualPrice = Integer.parseInt(priceElement.getText().replaceAll("[^0-9]", ""));
			        if (actualPrice != expectedPrice) {
			            throw new AssertionError("Price mismatch for product: " + productName);
			        }
			    }
			
			
		}

		@When("User validate the Total sum")
		public void user_validate_the_total_sum() {
			 int expectedTotal = cartProducts.values().stream().mapToInt(Integer::intValue).sum();;

		        WebElement totalElement = driver.findElement(By.xpath("(//*[contains(text(),' ₹')])[2]"));
		        int actualTotal = Integer.parseInt(totalElement.getText().replaceAll("[^0-9]", ""));

		        if (expectedTotal==actualTotal) {
			            throw new AssertionError("Total mismatch. Expected: " + expectedTotal + ", Found: " + actualTotal);
		        }	
			
		}

	    @After
	    public void tearDown() {
	         
	             driver.quit();
	        
	    }
	    }
	
