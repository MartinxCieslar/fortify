package com.fortify.demo.steps.libraries.actions;

import static org.assertj.core.api.Assertions.assertThat;

import com.fortify.demo.domain.WispererItem;
import com.fortify.demo.steps.libraries.page_objects.HeaderPage;
import java.util.List;
import java.util.stream.Collectors;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Step;
import org.assertj.core.api.SoftAssertions;


public class WispererActions {

    private final String WISPERER_ITEMS_KEY = WispererActions.class.getName() + ".wispererItems";

    HeaderPage headerPage;

    private void invalidateWispererItems() {
        Serenity.clearSessionVariable(WISPERER_ITEMS_KEY);
    }

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
        headerPage.searchInput
            .waitUntilVisible()
            .type(input);
    }

    @Step
    public void verifyEachItemInSectionContainsText(final String sectionName, final String expectedText) {
        SoftAssertions.assertSoftly(
            softly -> {
                getItemTextForSection(sectionName).forEach(
                    item -> softly.assertThat(item)
                        .as("Item '%s' under wisperer section '%s' don't contains text: %s", item, sectionName, expectedText)
                        .containsIgnoringCase(expectedText)
                );
            }
        );
    }

    @Step
    public void verifyWispererHasAllSectionNames(List<String> expectedSections) {
        assertThat(headerPage.getSectionNames())
            .as("Expected sections in wisperer table are not displayed.")
            .containsAll(expectedSections);
    }

    private List<String> getItemTextForSection(String sectionName) {
        return getWispererItems().stream()
            .filter(wispererItem -> wispererItem.getSectionName().equalsIgnoreCase(sectionName))
            .map(WispererItem::getItemName)
            .collect(Collectors.toList());
    }

}
