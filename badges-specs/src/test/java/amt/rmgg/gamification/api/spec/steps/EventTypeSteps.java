package amt.rmgg.gamification.api.spec.steps;

import amt.rmgg.gamification.ApiException;
import amt.rmgg.gamification.api.dto.EventType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertEquals;

public class EventTypeSteps {

    EventType eventType;
    EventType lastReceivedEventType;

    @Given("I have an eventType payload")
    public void iHaveAnEventTypePayload() {
        eventType = new EventType()
                .name("upvote")
                .initialValue(0);
    }

    @When("^I POST the eventType payload to the /eventTypes endpoint$")
    public void I_POST_the_eventType_payload_to_the_eventTypes_endpoint() {
        try {
            StepsHelper.lastApiResponse = StepsHelper.api.createEventTypeWithHttpInfo(eventType);
            StepsHelper.processApiResponse(StepsHelper.lastApiResponse);
            lastReceivedEventType = (EventType) StepsHelper.lastApiResponse.getData();
            StepsHelper.lastCreatedEventTypeId = ((EventType) StepsHelper.lastApiResponse.getData()).getId().intValue();
        } catch (ApiException e) {
            StepsHelper.processApiException(e);
        }
    }


    @Then("I receive an eventType that is the same as the payload")
    public void iReceiveAnEventTypeThatIsTheSameAsThePayload() {
        assertEquals(lastReceivedEventType, eventType);
    }

    @When("^I send a GET to the /eventTypes endpoint$")
    public void iSendAGETToTheEventTypesEndpoint() {
        try {
            StepsHelper.lastApiResponse = StepsHelper.api.getEventTypesWithHttpInfo();
            StepsHelper.processApiResponse(StepsHelper.lastApiResponse);
        } catch (ApiException e) {
            StepsHelper.processApiException(e);
        }
    }

    @Then("I GET an eventType with ID {int}")
    public void iGETAnEventTypeWithID(int id) {
        try {
            StepsHelper.lastApiResponse = StepsHelper.api.getEventTypeWithHttpInfo(id);
            StepsHelper.processApiResponse(StepsHelper.lastApiResponse);
        } catch (ApiException e) {
            StepsHelper.processApiException(e);
        }
    }

}
