package com.fortify.demo.steps.libraries.page_objects;

import static org.junit.jupiter.api.Assertions.fail;

import com.fortify.demo.domain.WispererItem;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;

@Slf4j
public class HeaderPage extends PageObject {

    public static final String SUGGESTIONS_SECTION_NAME = "Suggestions";
    private static final By WISPERER_SECTION_SELECTOR = By.cssSelector("li.ui-state-disabled");

    @FindBy(css = "#logo > a[title=Alza]")
    public WebElementFacade logoIcon;

    @FindBy(css = "#edtSearch")
    public WebElementFacade searchInput;

    @FindBy(css = ".newWisperer")
    private WebElementFacade wisperer;

    @FindBy(css = ".newWisperer li")
    private List<WebElementFacade> wispererItems;

    public List<String> getSectionNames() {
        if (!wisperer.isPresent()) {
            fail("Wisperer web element is not present on the page near the search field!");
        }
        if (!wisperer.isDisplayed()) {
            //open the wisperer dialog?
            //once it is searched, the table is in the DOM regardless of visibility
        }

        return wisperer.thenFindAll(WISPERER_SECTION_SELECTOR)
            .stream()
            .map(elm -> (WebElementFacade) elm.thenFind(".groupLabel"))
            .map(WebElementFacade::getText)
            .collect(Collectors.toList());
    }

    /**
     * Name of the first section is not present in the DOM, so we named it {@code Suggestions}
     *
     * @return a list of Wisperer items marked by section name
     */
    public List<WispererItem> getWispererItems() {
        List<String> sectionNames = new ArrayList<>() {{
            add(SUGGESTIONS_SECTION_NAME);
        }}; // first items don't have section
        sectionNames.addAll(getSectionNames());

        List<WispererItem> result = new ArrayList<>();
        int sectionIdx = 0;
        List<WebElementFacade> wispererItems = this.wispererItems;

        for (WebElementFacade item : wispererItems) {
            if (isSectionItem().test(item)) {
                sectionIdx++;
                continue;
            }

            result.add(
                WispererItem.builder()
                    .sectionName(sectionNames.get(sectionIdx))
                    .itemName(item.getText())
                    .highlightedText(getHighlightedText(item))
                    .build());
        }

        log.debug("Wisperer items registered: {}", result);
        return result;
    }

    private String getHighlightedText(WebElementFacade item) {
        List<WebElementFacade> items = item.thenFindAll(".//span[contains(@class,\"highlight\")]");
        return items.isEmpty() ? "" : items.get(0).getText();
    }

    private Predicate<WebElementFacade> isSectionItem() {
        Serenity.getDriver().manage().timeouts().implicitlyWait(Duration.ofMillis(1));
        return elm -> !elm.thenFindAll(By.cssSelector(".groupLabel")).isEmpty();
    }

}
