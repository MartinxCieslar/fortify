package com.fortify.demo.steps.definitions;

import static com.fortify.demo.steps.libraries.page_objects.ProductCategoriesPanel.extractLink;
import static com.fortify.demo.steps.libraries.page_objects.ProductCategoriesPanel.getCategoryName;
import static com.fortify.demo.steps.libraries.page_objects.ProductCategoriesPanel.hasImage;
import static com.fortify.demo.steps.libraries.page_objects.ProductCategoriesPanel.hasLink;
import static org.assertj.core.api.Assertions.assertThat;

import com.fortify.demo.steps.libraries.actions.LinkVerifier;
import com.fortify.demo.steps.libraries.page_objects.ActionDialog;
import com.fortify.demo.steps.libraries.page_objects.HeaderPage;
import com.fortify.demo.steps.libraries.page_objects.HomePage;
import com.fortify.demo.steps.libraries.page_objects.ProductCategoriesPanel;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import net.thucydides.core.annotations.Steps;
import org.assertj.core.api.SoftAssertions;

@Slf4j
public class HomePageSteps {

    HomePage homePage;
    ActionDialog actionDialog;
    ProductCategoriesPanel productCategoriesPanel;
    HeaderPage headerPageAction;

    @Steps
    LinkVerifier linkVerifier;

    @Given("I open Alza shop page")
    public void openShopPage() {
        if (!headerPageAction.logoIcon.isCurrentlyVisible()) {
            homePage.open();
            actionDialog.closeDialogIfDisplayed();
        } else {
            log.debug("Alza page is already opened.");
        }
    }

    @Then("I see product categories:")
    public void verifyProductCategoriesAreDisplayed(List<String> expectedCategories) {
        List<String> categoryNames = productCategoriesPanel.getCategoryNames();

        assertThat(categoryNames).as("Product categories must be present!").isNotEmpty();
        assertThat(categoryNames).containsOnly(expectedCategories.toArray(new String[0]));
    }

    @Then("I see that each category has leading image")
    public void verifyEachCategoryHasLeadingImage() {
        SoftAssertions.assertSoftly(
            softly -> productCategoriesPanel.categories.forEach(
                category -> softly.assertThat(hasImage().apply(category))
                    .as("Category '%s' doesn't have leading image.", getCategoryName().apply(category))
                    .isTrue()
            )
        );
    }

    @Then("I see that each category has valid link")
    public void verifyEachCategoryHasValidLInk() {
        SoftAssertions.assertSoftly(
            softly -> productCategoriesPanel.categories.forEach(
                category -> {
                    String categoryName = getCategoryName().apply(category);
                    softly.assertThat(hasLink().apply(category))
                        .as("Category '%s' doesn't have link.", categoryName)
                        .isTrue();
                    String href = extractLink().apply(category);
                    boolean isLinkBroken = linkVerifier.isLinkBroken(href);
                    softly.assertThat(isLinkBroken)
                        .as("Category '%s' has broken link. Href: %s", categoryName, href)
                        .isFalse();
                }
            )
        );
    }

}
