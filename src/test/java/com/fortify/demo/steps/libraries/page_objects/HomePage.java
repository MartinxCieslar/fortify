package com.fortify.demo.steps.libraries.page_objects;

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

        System.out.printf("navigator.userAgent: %s%n", getJavascriptExecutorFacade().executeScript("return navigator.userAgent"));
        System.out.printf("window.navigator.userAgent: %s%n", getJavascriptExecutorFacade().executeScript("return window.navigator.userAgent"));
        System.out.printf("navigator.webdriver, is headless: %s%n",
                          getJavascriptExecutorFacade().executeScript("return navigator.webdriver"));
        System.out.printf("!window.chrome, is headless: %s%n", getJavascriptExecutorFacade().executeScript("return !window.chrome"));
        System.out.printf("navigator.permissions, is headless: %s%n",
                          getJavascriptExecutorFacade().executeScript("return navigator.permissions.query({name:'notifications'}).then(function(permissionStatus) {return (Notification.permission === 'denied' && permissionStatus.state === 'prompt');});"));
        System.out.printf("navigator.plugins, can be headless: %s%n",
                          getJavascriptExecutorFacade().executeScript("return navigator.plugins.length === 0"));
        System.out.printf("navigator.languages, is headless: %s%n",
                          getJavascriptExecutorFacade().executeScript("return navigator.languages === \"\""));
        System.out.printf("navigator.languages, list: %s%n",
                          getJavascriptExecutorFacade().executeScript("return navigator.languages"));
    }

}
