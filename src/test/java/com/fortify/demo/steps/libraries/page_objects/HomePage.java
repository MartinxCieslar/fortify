package com.fortify.demo.steps.libraries.page_objects;

import static java.lang.String.format;

import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.annotations.DefaultUrl;

@Slf4j
@DefaultUrl("page:alza.app.url")
public class HomePage extends PageObject {

    public void logSessionDetails() {
        getDriver().manage().getCookies().forEach(
            cookie ->
                System.out.printf("Cookie Name: %s  Value: %s%n", cookie.getName(), cookie.getValue())
        );
        System.out.printf("Cdp session: %s%n", getDevTools().getCdpSession());

        //System.out.printf("", getDevTools().getDomains().network().setUserAgent(););
        System.out.printf("User agent: %s%n", getJavascriptExecutorFacade().executeScript("return navigator.userAgent"));

    }

}
