package com.fortify.demo.runners.integration;

import static com.fortify.demo.runners.LocalGlue.LOCAL;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
    tags = "@Integration and (not(@fixme or @Fixme or @FIXME))",
    plugin = "pretty",
    features = {"classpath:features"},
    glue = {
        LOCAL
    }
)
public class IntegrationTestRunner {

}
