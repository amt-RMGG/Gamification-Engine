package amt.rmgg.gamification.api.spec.steps;

import amt.rmgg.gamification.ApiException;
import amt.rmgg.gamification.api.dto.ApiKey;
import amt.rmgg.gamification.api.dto.Application;
import amt.rmgg.gamification.api.dto.Badge;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;

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

    @When("I GET the created badge")
    public void i_get_the_created_badge() {
        try {
            StepsHelper.lastApiResponse = StepsHelper.api.getBadgeWithHttpInfo(StepsHelper.lastCreatedBadgeId);
            StepsHelper.processApiResponse(StepsHelper.lastApiResponse);
            lastReceivedBadge = (Badge)StepsHelper.lastApiResponse.getData();
            assertEquals(lastReceivedBadge.getName(), badge.getName());
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

    @Given("I have another badge payload")
    public void i_have_another_badge_payload() throws Throwable {
        badge = new amt.rmgg.gamification.api.dto.Badge()
                .name("Petit pied")
                .experienceValue(250);
    }

    @When("^I POST the badge payload to the /badges endpoint$")
    public void i_POST_the_badge_payload_to_the_badges_endpoint() throws Throwable {
        try {
            StepsHelper.lastApiResponse = StepsHelper.api.createBadgeWithHttpInfo(badge);
            //Get the badge response
            lastReceivedBadge = (Badge) StepsHelper.lastApiResponse.getData();
            StepsHelper.lastCreatedBadgeId = lastReceivedBadge.getId().intValue();
            StepsHelper.processApiResponse(StepsHelper.lastApiResponse);
        } catch (ApiException e) {
            StepsHelper.processApiException(e);
        }
    }

    @And("I receive a payload that is the same as the badge payload")
    public void iReceiveAPayloadThatIsTheSameAsTheBadgePayload() {
        assertEquals(badge.getName(), lastReceivedBadge.getName());
        assertEquals(badge.getExperienceValue(), lastReceivedBadge.getExperienceValue());
    }

    @Then("I receive badges of my application only")
    public void iReceiveBadgesOfMyApplicationOnly() {
        try {
            StepsHelper.lastApiResponse = StepsHelper.api.getBadgesWithHttpInfo();
            StepsHelper.processApiResponse(StepsHelper.lastApiResponse);
            ArrayList<Badge> lastResponse = (ArrayList<Badge>) StepsHelper.lastApiResponse.getData();
            assertEquals(lastResponse.size(), 1);
            lastReceivedBadge = lastResponse.get(0);
            assertEquals("Petit pied", lastReceivedBadge.getName());
        } catch (ApiException e) {
            StepsHelper.processApiException(e);
        }
    }
}
