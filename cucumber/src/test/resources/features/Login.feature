Feature: Login Functionality
  #Background:
    #Given: User is on the Signin Page

  Scenario: Verify user is shown a valid error message on entering invalid credentials
    When The user enters invalid credentials
      | test | password |
    Then The user should see the error message

  Scenario: Verify user is able to access Forgot Password Page
    When The user clicks on 'Forgot Password'
    Then The user should land on Forgot Password page