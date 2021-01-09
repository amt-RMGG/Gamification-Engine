package amt.rmgg.gamification.api.spec.steps;

import amt.rmgg.gamification.ApiException;
import amt.rmgg.gamification.api.dto.Badge;
import amt.rmgg.gamification.api.dto.Rule;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertEquals;

public class RuleSteps {

    Rule rule;
    Rule lastReceivedRule;

    @Given("i have a rule payload")
    public void iHaveARulePayload() {
        rule = new amt.rmgg.gamification.api.dto.Rule()
                .badgeId(1)
                .eventTypeId((long)1)
                .threshold(100);
    }

    @When("^I POST the rule payload to the /rules endpoint$")
    public void I_POST_the_rule_payload_to_the_rules_endpoint() {
        try {
            StepsHelper.lastApiResponse = StepsHelper.api.createRuleWithHttpInfo(rule);
            StepsHelper.processApiResponse(StepsHelper.lastApiResponse);
            lastReceivedRule = (Rule)StepsHelper.lastApiResponse.getData();
        } catch (ApiException e) {
            StepsHelper.processApiException(e);
        }
    }

    @Then("I receive a rule that is the same as the payload")
    public void iReceiveARuleThatIsTheSameAsThePayload() {
        assertEquals(lastReceivedRule, rule);
    }

    @When("^I send a GET to the /rules endpoint$")
    public void iSendAGETToTheRulesEndpoint() {
        try {
            StepsHelper.lastApiResponse = StepsHelper.api.getRulesWithHttpInfo();
            StepsHelper.processApiResponse(StepsHelper.lastApiResponse);
        } catch (ApiException e) {
            StepsHelper.processApiException(e);
        }
    }

    @Then("I GET a rule with ID {int}")
    public void iGETARuleWithID(int id) {
        try {
            StepsHelper.lastApiResponse = StepsHelper.api.getRuleWithHttpInfo(id);
            StepsHelper.processApiResponse(StepsHelper.lastApiResponse);
        } catch (ApiException e) {
            StepsHelper.processApiException(e);
        }
    }

}
