package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

	@RunWith(Cucumber.class)
	@CucumberOptions(
	        features = "src/test/resources/Features",
	        glue = {"stepDefinations"},
	        tags = "@flipcart1",
	        plugin = {"pretty", "html:target/cucumber-reports.html"},
	        dryRun = false
	       
	)
	public class TestRunner {
	}
