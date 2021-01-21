package amt.rmgg.gamification.api.spec.steps;

import amt.rmgg.gamification.ApiException;
import amt.rmgg.gamification.api.dto.EventCounter;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertEquals;

public class EventCounterSteps {

    EventCounter eventCounter;
    EventCounter lastReceivedEventCounter;

    @Given("I have an eventCounter payload")
    public void iHaveAnEventCounterPayload() {
        eventCounter = new EventCounter()
                .name("upvote")
                .initialValue(0);
    }

    @When("^I POST the eventCounter payload to the /eventCounters endpoint$")
    public void I_POST_the_eventCounter_payload_to_the_eventCounters_endpoint() {
        try {
            StepsHelper.lastApiResponse = StepsHelper.api.createEventCounterWithHttpInfo(eventCounter);
            StepsHelper.processApiResponse(StepsHelper.lastApiResponse);
            lastReceivedEventCounter = (EventCounter) StepsHelper.lastApiResponse.getData();
            StepsHelper.lastCreatedEventCounterId = ((EventCounter) StepsHelper.lastApiResponse.getData()).getId().intValue();
        } catch (ApiException e) {
            StepsHelper.processApiException(e);
        }
    }


    @Then("I receive an eventCounter that is the same as the payload")
    public void iReceiveAnEventCounterThatIsTheSameAsThePayload() {
        assertEquals(lastReceivedEventCounter, eventCounter);
    }

    @When("^I send a GET to the /eventCounters endpoint$")
    public void iSendAGETToTheEventCountersEndpoint() {
        try {
            StepsHelper.lastApiResponse = StepsHelper.api.getEventCountersWithHttpInfo();
            StepsHelper.processApiResponse(StepsHelper.lastApiResponse);
        } catch (ApiException e) {
            StepsHelper.processApiException(e);
        }
    }

    @Then("I GET an eventCounter with ID {int}")
    public void iGETAnEventCounterWithID(int id) {
        try {
            StepsHelper.lastApiResponse = StepsHelper.api.getEventCounterWithHttpInfo(id);
            StepsHelper.processApiResponse(StepsHelper.lastApiResponse);
        } catch (ApiException e) {
            StepsHelper.processApiException(e);
        }
    }

}
