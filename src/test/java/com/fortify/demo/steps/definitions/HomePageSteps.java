package com.fortify.demo.steps.definitions;

import static com.fortify.demo.steps.libraries.page_objects.ProductCategoriesPanel.getCategoryName;
import static com.fortify.demo.steps.libraries.page_objects.ProductCategoriesPanel.hasImage;
import static org.assertj.core.api.Assertions.assertThat;

import com.fortify.demo.steps.libraries.page_objects.ActionDialog;
import com.fortify.demo.steps.libraries.page_objects.HomePage;
import com.fortify.demo.steps.libraries.page_objects.ProductCategoriesPanel;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import java.util.List;
import org.assertj.core.api.SoftAssertions;

public class HomePageSteps {

    HomePage homePage;
    ActionDialog actionDialog;
    ProductCategoriesPanel productCategoriesPanel;

    @Given("I open Alza shop page")
    public void openShopPage() {
        homePage.open();
        actionDialog.closeDialogIfDisplayed();
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

}