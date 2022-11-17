package com.fortify.demo.steps.definitions;

import com.fortify.demo.domain.SectionVerification;
import com.fortify.demo.steps.libraries.actions.WispererActions;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.List;
import net.thucydides.core.annotations.Steps;

public class HeaderPageSteps {

    @Steps
    WispererActions wispererActions;

    @When("I type term {string} to search field")
    public void userTypesTermToSearchField(String input) {
        wispererActions.typeTextToSearchField(input);
    }

    @Then("I see autocomplete sections:")
    public void verifyAutocompleteSections(List<String> expectedSections) {
        wispererActions.verifyWispererHasAllSectionNames(expectedSections);
    }

    /**
     * First section is named as {@code Suggestions}
     *
     * @param sectionName a name of the section as filter for items
     */
    @Then("I see that all items in section {string} contains {string}")
    public void verifySectionItemsContainsText(String sectionName, String expectedText) {
        wispererActions.verifyWispererHasSectionName(sectionName);
        wispererActions.verifyEachItemInSectionContainsText(sectionName, expectedText);
    }

    @Then("I see that section has no more than items count:")
    public void verifySectionHasNoMoreThenXItems(List<SectionVerification> items) {
        items.forEach(
            item -> wispererActions.verifySectionHasNoMoreThenXItems(item)
        );
    }

    @Then("I see that wisperer contains only {string}")
    public void verifySectionHasNoMoreThenXItems(String expectedText) {
        wispererActions.verifyEachItemInSectionContainsText("", expectedText);
    }

}
