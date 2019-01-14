package io.sandeep.framework.cucumber.stepdefinitions;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;
import io.sandeep.framework.cucumber.context.TestContext;
import io.sandeep.framework.cucumber.enums.Context;
import io.sandeep.framework.cucumber.stepdefinitions.base.BaseStepDefinition;
import io.sandeep.framework.pages.ForgotPasswordPage;
import io.sandeep.framework.pages.LoginErrorPage;
import io.sandeep.framework.pages.LoginPage;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class LoginStepdefs extends BaseStepDefinition {
    private LoginPage loginPage;
    private LoginErrorPage login_error_page;
    private ForgotPasswordPage forgot_password_page;

    public LoginStepdefs (TestContext context) {
        super(context);
        loginPage = (LoginPage) context.getScenarioContext().getContext(Context.PAGE_OBJECTS.LOGIN.toString());
    }

    @When("^The user enters invalid credentials$")
    public void the_user_enters_invalid_credentials (DataTable user_credentials) {
        List <List <String>> data = user_credentials.asLists();

        login_error_page = loginPage.attemptSignIn(data.get(0).get(0), data.get(0).get(1));
    }

    @Then("The user should see the error message")
    public void the_user_should_see_the_error_message () {
        assertThat(login_error_page.getErrorText(), is("Sorry, your account is disabled. Please contact LEVERTON Support for assistance."));
    }

    @When("The user clicks on {string}")
    public void theUserClicksOnForgotPassword (String string_to_click) {
        forgot_password_page = loginPage.navigateToLostPassword();
    }

    @Then("The user should land on Forgot Password page")
    public void theUserShouldLandOnForgotPasswordPage () {
        assertThat(forgot_password_page.getPageTitle(), is("Forgot password"));
    }
}