package amt.rmgg.gamification.api.spec.steps;

import amt.rmgg.gamification.ApiException;
import amt.rmgg.gamification.ApiResponse;
import amt.rmgg.gamification.api.DefaultApi;
import amt.rmgg.gamification.api.dto.ApiKey;
import amt.rmgg.gamification.api.dto.Application;
import amt.rmgg.gamification.api.dto.Badge;
import amt.rmgg.gamification.api.dto.Event;
import amt.rmgg.gamification.api.spec.helpers.Environment;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
public class EventSteps {
    private Environment environment;
    private DefaultApi api;

    private ApiResponse lastApiResponse;
    private ApiException lastApiException;
    private boolean lastApiCallThrewException;
    private int lastStatusCode;

    private String lastReceivedLocationHeader;

    Event event;

    public EventSteps(Environment environment) {
        this.environment = environment;
        this.api = environment.getApi();
    }

    //TODO : Refactorisé ça avec BasicSteps

    private void processApiResponse(ApiResponse apiResponse) {
        lastApiResponse = apiResponse;
        lastApiCallThrewException = false;
        lastApiException = null;
        lastStatusCode = lastApiResponse.getStatusCode();
        List<String> locationHeaderValues = (List<String>)lastApiResponse.getHeaders().get("Location");
        lastReceivedLocationHeader = locationHeaderValues != null ? locationHeaderValues.get(0) : null;
    }

    private void processApiException(ApiException apiException) {
        lastApiCallThrewException = true;
        lastApiResponse = null;
        lastApiException = apiException;
        lastStatusCode = lastApiException.getCode();
    }


    @Given("I have a event payload")
    public void i_have_a_event_payload() throws Throwable {
        event = new Event()
                .name("firstUpvote")
                .userid(1);
    }

    @When("^I POST the event payload to the /events endpoint$")
    public void i_POST_the_event_payload_to_the_events_endpoint() throws Throwable {
        try {
            lastApiResponse = api.sendEventWithHttpInfo(event);
            processApiResponse(lastApiResponse);
        } catch (ApiException e) {
            processApiException(e);
        }
    }
}
