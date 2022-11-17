package com.fortify.demo.steps.libraries.actions;

import static java.util.Objects.isNull;
import static org.assertj.core.api.Assertions.assertThat;

import com.fortify.demo.domain.SectionVerification;
import com.fortify.demo.domain.WispererItem;
import com.fortify.demo.steps.libraries.page_objects.HeaderPage;
import java.util.List;
import java.util.stream.Collectors;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;
import org.assertj.core.api.SoftAssertions;


public class WispererActions {

    private static final String EXCLUDE_SHOW_ALL_RESULTS = "Show all results";
    private static final String WISPERER_ITEMS_KEY = WispererActions.class.getName() + ".wispererItems";

    HeaderPage headerPage;

    private void invalidateWispererItems() {
        Serenity.clearSessionVariable(WISPERER_ITEMS_KEY);
    }

    /**
     * remember wisperer items for quicker checks between cucumber steps until new text is entered to search field.
     * The remembered values are invalidated by {@link this#invalidateWispererItems()}
     */
    private List<WispererItem> getWispererItems() {
        if (!Serenity.hasASessionVariableCalled(WISPERER_ITEMS_KEY)) {
            Serenity.setSessionVariable(WISPERER_ITEMS_KEY).to(headerPage.getWispererItems());
        }
        return Serenity.sessionVariableCalled(WISPERER_ITEMS_KEY);
    }

    @Step
    public void verifyWispererHasSectionName(String sectionName) {
        List<String> sectionNames = headerPage.getSectionNames();
        assertThat(sectionNames).as("There are no sections in the wisperer.").isNotEmpty();

        sectionNames.add(HeaderPage.SUGGESTIONS_SECTION_NAME);

        assertThat(sectionNames.stream().map(String::toLowerCase).collect(Collectors.toList()))
            .as("Unknown section '%s'. Available sections are: %s", sectionName, sectionNames)
            .contains(sectionName.toLowerCase());
    }

    @Step
    public void typeTextToSearchField(String input) {
        invalidateWispererItems();
        WebElementFacade wispererItem = headerPage.oneWispererItem();
        headerPage.searchInput.waitUntilVisible().clear();
        headerPage.waitUntilStale(wispererItem);
        headerPage.searchInput.type(input);
    }

    @Step
    public void verifyEachItemInSectionContainsText(final String sectionName, final String expectedText) {
        List<String> items = isNull(sectionName) || sectionName.isBlank() ?
            getWispererItems().stream().map(WispererItem::getItemName).collect(Collectors.toList()) :
            getItemTextForSection(sectionName);

        SoftAssertions.assertSoftly(
            softly -> items.forEach(
                item -> softly.assertThat(item)
                    .as("Item '%s' under wisperer section '%s' don't contains text: %s",
                        item,
                        sectionName,
                        expectedText)
                    .containsIgnoringCase(expectedText))
        );
    }

    @Step
    public void verifyWispererHasAllSectionNames(List<String> expectedSections) {
        assertThat(headerPage.getSectionNames())
            .as("Expected sections in wisperer table are not displayed.")
            .containsAll(expectedSections);
    }

    @Step("Verify section '{0.sectionName}' has no more then '{0.maxItemCount}' items")
    public void verifySectionHasNoMoreThenXItems(SectionVerification sectionVerification) {
        assertThat(getItemTextForSection(sectionVerification.getSectionName()))
            .hasSizeLessThanOrEqualTo(sectionVerification.getMaxItemCount());
    }

    /**
     * Filter "Show all results", as it is not part of the sections.
     *
     * @param sectionName a name of section for which the items will be returned
     * @return a filtered list by section
     */
    private List<String> getItemTextForSection(String sectionName) {
        return getWispererItems().stream()
            .filter(wispererItem -> wispererItem.getSectionName().equalsIgnoreCase(sectionName))
            .map(WispererItem::getItemName)
            .filter(EXCLUDE_SHOW_ALL_RESULTS::equalsIgnoreCase)
            .collect(Collectors.toList());
    }

}
