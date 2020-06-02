package puertoricotr;

import org.junit.runner.RunWith;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features")
public class RunCucumberIntegrationTest extends SpringIntegrationTest { // will run all features found on the classpath in the same package as this class
}