package com.fortify.demo.steps.libraries.page_objects;

import static com.fortify.demo.steps.libraries.Utils.test;
import static java.util.Objects.isNull;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.core.pages.WebElementState;
import org.openqa.selenium.By;

public class ProductCategoriesPanel extends PageObject {

    public static final String IMAGE_EXTENSION_REGEX = "(\\.jpg$|\\.svg$|\\.png$)";
    @FindBy(css = "#tpf > li")
    public List<WebElementFacade> categories;

    public List<String> getCategoryNames() {
        return categories.stream()
            .filter(WebElementState::isCurrentlyVisible)
            .map(getCategoryName())
            .collect(Collectors.toList());
    }

    public static Function<WebElementFacade, Boolean> hasImage() {
        return elm -> {
            WebElementFacade image = elm.find(By.cssSelector(".icon > img"));
            return image.isPresent()
                && image.getAttribute("src").contains("/Foto/")
                && test(image.getAttribute("src"), IMAGE_EXTENSION_REGEX);
        };
    }

    public static Function<WebElementFacade, Boolean> hasLink() {
        return elm -> {
            WebElementFacade link = elm.find(By.cssSelector("a"));
            return link.isPresent();
        };
    }

    public static Function<WebElementFacade, String> extractLink() {
        return elm -> {
            WebElementFacade link = elm.find(By.cssSelector("a"));
            String href = link.getAttribute("href");
            return isNull(href) ? "" : href;
        };
    }

    public static Function<WebElementFacade, String> getCategoryName() {
        return elm -> elm.find(By.cssSelector("a")).getText();
    }

}
