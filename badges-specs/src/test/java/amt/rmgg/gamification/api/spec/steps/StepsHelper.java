package amt.rmgg.gamification.api.spec.steps;

import amt.rmgg.gamification.ApiException;
import amt.rmgg.gamification.ApiResponse;
import amt.rmgg.gamification.api.DefaultApi;
import amt.rmgg.gamification.api.dto.ApiKey;
import amt.rmgg.gamification.api.dto.Application;
import amt.rmgg.gamification.api.dto.Badge;
import amt.rmgg.gamification.api.spec.helpers.Environment;

import java.util.List;

public class StepsHelper {

    public static ApiKey apiKey;
    public static Application app;

    public static ApiResponse lastApiResponse;
    public static ApiException lastApiException;
    public static boolean lastApiCallThrewException;
    public static int lastStatusCode;

    public static Environment environment;
    public static DefaultApi api;

    public static String lastReceivedLocationHeader;

    public static void processApiResponse(ApiResponse apiResponse) {
        lastApiResponse = apiResponse;
        lastApiCallThrewException = false;
        lastApiException = null;
        lastStatusCode = StepsHelper.lastApiResponse.getStatusCode();
        List<String> locationHeaderValues = (List<String>)lastApiResponse.getHeaders().get("Location");
        lastReceivedLocationHeader = locationHeaderValues != null ? locationHeaderValues.get(0) : null;
    }

    public static void processApiException(ApiException apiException) {
        lastApiCallThrewException = true;
        lastApiResponse = null;
        lastApiException = apiException;
        lastStatusCode = StepsHelper.lastApiException.getCode();
    }


}
