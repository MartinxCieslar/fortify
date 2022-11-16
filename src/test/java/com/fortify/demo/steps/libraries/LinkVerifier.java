package com.fortify.demo.steps.libraries;

import static net.serenitybdd.core.environment.EnvironmentSpecificConfiguration.from;

import io.restassured.http.Method;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import org.apache.hc.core5.http.HttpStatus;

public class LinkVerifier {

    private static final String ALZA_BASE_URI = "alza.app.base.url";

    private EnvironmentVariables environmentVariables;

    public boolean isLinkBroken(String url) {
        Response response = sendRequest(Method.GET, url);
        return response.getStatusCode() != HttpStatus.SC_OK;
    }

    public Response sendRequest(Method httpMethod, String url) {
        return SerenityRest.given()
            .baseUri(getBaseUri())
            .request(httpMethod, url);
    }

    public String getBaseUri() {
        return from(environmentVariables).getProperty(ALZA_BASE_URI);
    }

}
