package amt.rmgg.gamification.api.spec.steps;

import amt.rmgg.gamification.ApiException;
import amt.rmgg.gamification.ApiResponse;
import amt.rmgg.gamification.api.DefaultApi;
import amt.rmgg.gamification.api.dto.Event;
import amt.rmgg.gamification.api.spec.helpers.Environment;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import java.util.List;
public class EventSteps {

    Event event;

    @Given("I have a event payload")
    public void i_have_a_event_payload() throws Throwable {
        event = new Event()
                .userid(1);
    }

    @When("^I POST the event payload to the /events endpoint$")
    public void i_POST_the_event_payload_to_the_events_endpoint() {
        try {
            StepsHelper.lastApiResponse = StepsHelper.api.sendEventWithHttpInfo(event);
            StepsHelper.processApiResponse(StepsHelper.lastApiResponse);
        } catch (ApiException e) {
            StepsHelper.processApiException(e);
        }
    }
}
