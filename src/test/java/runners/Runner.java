package runners;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty",
                "json:target/cucumber.json",},
        features = "src/test/resources/Features",
        glue = {"stepDefinitions"},
        tags = "@ORTAMOLUSTURMA",
        dryRun = false,
        publish = true



)
public class Runner {
}
