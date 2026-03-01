package com.example;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.RequestOptions;
import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;

import java.util.HashMap;
import java.util.Map;

@RobotKeywords
public class ApiKeywords {

    private Playwright playwright;
    private APIRequestContext requestContext;
    private APIResponse lastResponse;

    @RobotKeyword("Create API Session")
    @ArgumentNames({ "baseUrl" })
    public void createApiSession(String baseUrl) {
        if (playwright == null) {
            playwright = Playwright.create();
        }
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        requestContext = playwright.request().newContext(new APIRequest.NewContextOptions()
                .setBaseURL(baseUrl)
                .setExtraHTTPHeaders(headers));
    }

    @RobotKeyword("GET Request")
    @ArgumentNames({ "endpoint" })
    public void getRequest(String endpoint) {
        lastResponse = requestContext.get(endpoint);
        System.out.println("GET Response: " + lastResponse.status());
    }

    @RobotKeyword("POST Request")
    @ArgumentNames({ "endpoint", "body" })
    public void postRequest(String endpoint, String body) {
        lastResponse = requestContext.post(endpoint, RequestOptions.create().setData(body));
        System.out.println("POST Response: " + lastResponse.status());
    }

    @RobotKeyword("Response Status Should Be")
    @ArgumentNames({ "statusCode" })
    public void responseStatusShouldBe(int statusCode) {
        if (lastResponse.status() != statusCode) {
            throw new RuntimeException("Expected " + statusCode + " but got " + lastResponse.status());
        }
    }

    @RobotKeyword("Close API Session")
    public void closeApiSession() {
        if (requestContext != null) {
            requestContext.dispose();
            requestContext = null;
        }
        if (playwright != null) {
            playwright.close();
            playwright = null;
        }
    }
}
