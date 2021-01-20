package amt.rmgg.gamification.api.spec.steps;

import amt.rmgg.gamification.ApiException;
import amt.rmgg.gamification.api.dto.ApiKey;
import amt.rmgg.gamification.api.dto.Application;
import amt.rmgg.gamification.api.spec.helpers.Environment;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import static org.junit.Assert.*;

public class BasicSteps {

    public BasicSteps(Environment environment) {
        StepsHelper.environment = environment;
        StepsHelper.api = environment.getApi();
    }

    @Given("there is a Badges server")
    public void there_is_a_Badges_server() throws Throwable {
        assertNotNull(StepsHelper.api);
    }

    @Given("the server is running")
    public void the_server_is_running() throws Throwable {
        assert(true);
    }


    @Then("I receive a {int} status code")
    public void i_receive_a_status_code(int expectedStatusCode) throws Throwable {
        assertEquals(expectedStatusCode, StepsHelper.lastStatusCode);
    }


    @Then("I receive a {int} status code with a location header")
    public void iReceiveAStatusCodeWithALocationHeader(int arg0) {
        //TODO
        assert(StepsHelper.lastApiResponse.getStatusCode() == arg0);
    }

    @Given("my application is register")
    public void myApplicationIsRegister() {
        StepsHelper.app = new Application()
                .description("A great app!")
                .name("TheSuperDuperApp");
        try {
            StepsHelper.lastApiResponse = StepsHelper.api.registerAppWithHttpInfo(StepsHelper.app);
        } catch (ApiException e) {
            e.printStackTrace();
            StepsHelper.processApiException(e);
        }
        StepsHelper.apiKey = (ApiKey) StepsHelper.lastApiResponse.getData();
        StepsHelper.api.getApiClient().addDefaultHeader("x-api-key", StepsHelper.apiKey.getUuid());
    }

    @Given("another application is register")
    public void anotherApplicationIsRegister() {
        StepsHelper.app = new Application()
                .description("Another great app!")
                .name("TheOtherSuperDuperApp");
        try {
            StepsHelper.lastApiResponse = StepsHelper.api.registerAppWithHttpInfo(StepsHelper.app);
        } catch (ApiException e) {
            e.printStackTrace();
            StepsHelper.processApiException(e);
        }
        StepsHelper.apiKey = (ApiKey) StepsHelper.lastApiResponse.getData();
        StepsHelper.api.getApiClient().addDefaultHeader("x-api-key", StepsHelper.apiKey.getUuid());
    }
}
