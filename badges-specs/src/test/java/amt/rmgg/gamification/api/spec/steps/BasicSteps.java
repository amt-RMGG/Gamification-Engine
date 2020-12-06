package amt.rmgg.gamification.api.spec.steps;

import amt.rmgg.gamification.ApiException;
import amt.rmgg.gamification.api.dto.*;
import amt.rmgg.gamification.api.spec.helpers.Environment;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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

    @Given("I register a eventType")
    public void iRegisterABadge() {
        try {
            Badge payload = new Badge().name("Super upvoter").experienceValue(10);
            StepsHelper.lastApiResponse = StepsHelper.api.createBadgeWithHttpInfo(payload);
            StepsHelper.processApiResponse(StepsHelper.lastApiResponse);
            StepsHelper.badge = (Badge) StepsHelper.lastApiResponse.getData();
        } catch (ApiException e) {
            StepsHelper.processApiException(e);
        }
    }

    @Given("I register a eventType")
    public void iRegisterAEventType() {
        try {
            EventType payload = new EventType().name("upvote");
            StepsHelper.lastApiResponse = StepsHelper.api.createEventTypeWithHttpInfo(payload);
            StepsHelper.processApiResponse(StepsHelper.lastApiResponse);
            StepsHelper.eventType = (EventType) StepsHelper.lastApiResponse.getData();
        } catch (ApiException e) {
            StepsHelper.processApiException(e);
        }
    }

    @Given("I register a rule with a threshold of {int}")
    public void iRegisterARuleWithAThresholdOf(int n) {
        iRegisterABadge();
        iRegisterAEventType();
        try {
            Rule payload = new Rule().badgeId(Math.toIntExact(StepsHelper.badge.getId()))
                    .eventTypeId(StepsHelper.eventType.getId())
                    .threshold(n);
            StepsHelper.lastApiResponse = StepsHelper.api.createRuleWithHttpInfo(payload);
            StepsHelper.processApiResponse(StepsHelper.lastApiResponse);
            StepsHelper.rule = (Rule)StepsHelper.lastApiResponse.getData();
        } catch (ApiException e) {
            StepsHelper.processApiException(e);
        }
    }
}
