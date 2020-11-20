package amt.rmgg.gamification.api.spec.steps;

import amt.rmgg.gamification.ApiException;
import amt.rmgg.gamification.ApiResponse;
import amt.rmgg.gamification.api.DefaultApi;
import amt.rmgg.gamification.api.dto.ApiKey;
import amt.rmgg.gamification.api.dto.Application;
import amt.rmgg.gamification.api.dto.Badge;
import amt.rmgg.gamification.api.spec.helpers.Environment;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BasicSteps {

    private Environment environment;
    private DefaultApi api;

    Badge badge;
    private ApiKey apiKey;
    private Application app;

    private ApiResponse lastApiResponse;
    private ApiException lastApiException;
    private boolean lastApiCallThrewException;
    private int lastStatusCode;

    private String lastReceivedLocationHeader;
    private Badge lastReceivedBadge;

    public BasicSteps(Environment environment) {
        this.environment = environment;
        this.api = environment.getApi();
    }

    @Given("there is a Badges server")
    public void there_is_a_Badges_server() throws Throwable {
        assertNotNull(api);
    }

    @Given("the server is running")
    public void the_server_is_running() throws Throwable {
        assert(true);
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
            lastApiResponse = api.createBadgeWithHttpInfo(badge);
            processApiResponse(lastApiResponse);
        } catch (ApiException e) {
            processApiException(e);
        }
    }

    @Then("I receive a {int} status code")
    public void i_receive_a_status_code(int expectedStatusCode) throws Throwable {
        assertEquals(expectedStatusCode, lastStatusCode);
    }

    @When("^I send a GET to the /badges endpoint$")
    public void iSendAGETToTheBadgesEndpoint() {
        try {
            lastApiResponse = api.getBadgesWithHttpInfo();
            processApiResponse(lastApiResponse);
        } catch (ApiException e) {
            processApiException(e);
        }
    }

    @Then("I receive a {int} status code with a location header")
    public void iReceiveAStatusCodeWithALocationHeader(int arg0) {
    }

    @When("I GET a badge with ID {int}")
    public void iGETABadge(int badgeId) {
        Integer id = Integer.parseInt(lastReceivedLocationHeader.substring(lastReceivedLocationHeader.lastIndexOf('/') + 1));
        try {
            lastApiResponse = api.getBadgeWithHttpInfo(badgeId);
            processApiResponse(lastApiResponse);
            lastReceivedBadge = (Badge)lastApiResponse.getData();
        } catch (ApiException e) {
            processApiException(e);
        }
    }

    @And("I receive a payload that is the same as the badge payload")
    public void iReceiveAPayloadThatIsTheSameAsTheBadgePayload() {
        assertEquals(badge, lastReceivedBadge);
    }

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

    @Given("my application is register")
    public void myApplicationIsRegister() {
        app = new Application()
                .description("A great app!")
                .name("TheSuperDuperApp");
        try {
            lastApiResponse = api.registerAppWithHttpInfo(app);
        } catch (ApiException e) {
            e.printStackTrace();
            processApiException(e);
        }
        apiKey = (ApiKey) lastApiResponse.getData();
        api.getApiClient().addDefaultHeader("x-api-key", apiKey.getUuid());
    }
}
