package com.fortify.demo.steps.libraries.page_objects;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;

public class ActionDialog extends PageObject {

    @FindBy(id = "alzaDialog")
    private WebElementFacade alzaDialog;

    @FindBy(css = "#alzaDialog .close")
    private WebElementFacade closeButton;

    public void closeDialogIfDisplayed() {
        //TODO if possible, find out what can be used as synchronization element for case, when the dialog is not present
        // by default the script will wait in default timeout: serenity.conf#timeouts#fluentwait
        if (alzaDialog.waitUntilPresent().isDisplayed()) {
            closeButton.click();
            alzaDialog.waitUntilNotVisible();
        }
    }

}
