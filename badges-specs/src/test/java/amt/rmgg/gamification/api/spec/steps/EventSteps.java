package amt.rmgg.gamification.api.spec.steps;

import amt.rmgg.gamification.ApiException;
import amt.rmgg.gamification.ApiResponse;
import amt.rmgg.gamification.api.DefaultApi;
import amt.rmgg.gamification.api.dto.Badge;
import amt.rmgg.gamification.api.dto.Event;
import amt.rmgg.gamification.api.dto.EventType;
import amt.rmgg.gamification.api.spec.helpers.Environment;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;
public class EventSteps {

    Event event;
    Event lastRecievedEvent;
    Badge receivedBadge;

    @Given("I have a event payload")
    public void i_have_a_event_payload() throws Throwable {
        event = new Event()
                .username("Jean-Pierre Bacri")
                .eventTypeId((long)StepsHelper.lastCreatedEventTypeId);
    }

    @When("^I POST the event payload to the /events endpoint$")
    public void i_POST_the_event_payload_to_the_events_endpoint() {
        try {
            StepsHelper.lastApiResponse = StepsHelper.api.sendEventWithHttpInfo(event);
            StepsHelper.processApiResponse(StepsHelper.lastApiResponse);
            // StepsHelper.lastCreatedEventId = ((Event) StepsHelper.lastApiResponse.getData()).getEventTypeId().intValue();
        } catch (ApiException e) {
            StepsHelper.processApiException(e);
        }
    }

    @When("I POST the event payload {int} times to the \\/events endpoint")
    public void iPOSTTheEventPayloadTimesToTheEventsEndpoint(int arg0) {
        for (int i = 0; i < arg0; i++) {
            try {
                StepsHelper.lastApiResponse = StepsHelper.api.sendEventWithHttpInfo(event);
                StepsHelper.processApiResponse(StepsHelper.lastApiResponse);
            } catch (ApiException e) {
                StepsHelper.processApiException(e);
            }
        }
    }

    @Then("I receive the award-badge")
    public void i_receive_the_award_badge(){
        receivedBadge = ((Badge) StepsHelper.lastApiResponse.getData());
        assert(receivedBadge != null);
        assert(receivedBadge.getId() == StepsHelper.lastCreatedBadgeId);
    }

    @And("I GET the created Events")
    public void iGETTheCreatedEvent() {
        try {
            StepsHelper.lastApiResponse(StepsHelper.api.getBadgeWithHttpInfo(StepsHelper.lastCreatedEventId));
            StepsHelper.processApiResponse(StepsHelper.lastApiResponse);
            lastRecievedEvent = (Event) StepsHelper.lastApiResponse.getData();
            assertEquals(lastRecievedEvent.getName(), event.getName());
        } catch (ApiException e) {
            StepsHelper.processApiException(e);
        }
    }

    @And("I GET a event with non existing ID")
    public void iGETTheCreatedEvent() {
        try {
            StepsHelper.lastApiResponse(StepsHelper.api.getBadgeWithHttpInfo(StepsHelper.lastCreatedEventId+1));
            StepsHelper.processApiResponse(StepsHelper.lastApiResponse);
            lastRecievedEvent = (Event) StepsHelper.lastApiResponse.getData();
        } catch (ApiException e) {
            StepsHelper.processApiException(e);
        }
    }

    @Then("I GET an event with ID {int}")
    public void iGETAnEventTypeWithID(int eventId) {
        try {
            StepsHelper.setLastApiResponse(StepsHelper.getApi().getEventWithHttpInfo(eventId));
            StepsHelper.processApiResponse(StepsHelper.getLastApiResponse());
            lastRecievedEvent = (Event) StepsHelper.lastApiResponse.getData();
        } catch (ApiException e) {
            StepsHelper.processApiException(e);
        }
    }

}
