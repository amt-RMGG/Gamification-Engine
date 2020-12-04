package amt.rmgg.gamification.api.spec.steps;

import amt.rmgg.gamification.ApiException;
import amt.rmgg.gamification.api.dto.Badge;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertEquals;

public class BadgeSteps  {

    private Badge badge;
    private Badge lastReceivedBadge;

    @When("^I send a GET to the /badges endpoint$")
    public void iSendAGETToTheBadgesEndpoint() {
        try {
            StepsHelper.lastApiResponse = StepsHelper.api.getBadgesWithHttpInfo();
            StepsHelper.processApiResponse(StepsHelper.lastApiResponse);
        } catch (ApiException e) {
            StepsHelper.processApiException(e);
        }
    }

    @When("I GET a badge with ID {int}")
    public void iGETABadge(int badgeId) {
        //Integer id = Integer.parseInt(lastReceivedLocationHeader.substring(lastReceivedLocationHeader.lastIndexOf('/') + 1));
        try {
            StepsHelper.lastApiResponse = StepsHelper.api.getBadgeWithHttpInfo(badgeId);
            StepsHelper.processApiResponse(StepsHelper.lastApiResponse);
            lastReceivedBadge = (Badge)StepsHelper.lastApiResponse.getData();
        } catch (ApiException e) {
            StepsHelper.processApiException(e);
        }
    }

    @Given("I have a badge payload")
    public void i_have_a_badge_payload() throws Throwable {
        badge = new amt.rmgg.gamification.api.dto.Badge()
                .name("Grosse tête")
                .experienceValue(100);
    }

    @When("^I POST the badge payload to the /badges endpoint$")
    public void i_POST_the_badge_payload_to_the_badges_endpoint() throws Throwable {
        try {
            StepsHelper.lastApiResponse = StepsHelper.api.createBadgeWithHttpInfo(badge);
            StepsHelper.processApiResponse(StepsHelper.lastApiResponse);
        } catch (ApiException e) {
            StepsHelper.processApiException(e);
        }
    }

    @And("I receive a payload that is the same as the badge payload")
    public void iReceiveAPayloadThatIsTheSameAsTheBadgePayload() {
        assertEquals(badge, lastReceivedBadge);
    }
}
