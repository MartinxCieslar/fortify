package com.fortify.demo.runners.debug;

import static com.fortify.demo.runners.LocalGlue.LOCAL;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
    tags = "@Debug or @debug",
    plugin = "pretty",
    features = {"classpath:features"},
    glue = {
        LOCAL
    }
)
public class DebugTestRunner {

}
