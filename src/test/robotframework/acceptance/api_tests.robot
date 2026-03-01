*** Settings ***
Library    com.example.ApiKeywords
Test Setup    Create API Session    ${BASE_URL}
Test Teardown    Close API Session

*** Variables ***
${BASE_URL}    https://jsonplaceholder.typicode.com
${POST_DATA}   {"title": "foo", "body": "bar", "userId": 1}

*** Test Cases ***
Verify User Data retrieval
    [Tags]    smoke
    When GET Request    /users/1
    Then Response Status Should Be    200

Verify User Data retrieval for invalid endpoint
    When GET Request    /users/1234567890
    Then Response Status Should Be    404

Verify Creating a new Post
    [Tags]    regression
    When POST Request    /posts    ${POST_DATA}
    Then Response Status Should Be    201


