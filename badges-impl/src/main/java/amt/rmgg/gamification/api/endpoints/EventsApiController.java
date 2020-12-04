package amt.rmgg.gamification.api.endpoints;

import amt.rmgg.gamification.api.EventsApi;
import amt.rmgg.gamification.api.model.Event;
import amt.rmgg.gamification.api.util.ApiKeyManager;
import amt.rmgg.gamification.api.util.EventProcessorService;
import amt.rmgg.gamification.entities.ApplicationEntity;
import amt.rmgg.gamification.repositories.EventTypeRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

public class EventsApiController implements EventsApi {

    @Autowired
    private ApiKeyManager apiKeyManager;

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    EventProcessorService eventProcessorService;

    @Autowired
    EventTypeRepository eventTypeRepository;

    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> sendEvent(@ApiParam @Valid @RequestBody Event event)
    {
        String apikey = httpServletRequest.getHeader("x-api-key");
        ApplicationEntity applicationEntity = apiKeyManager.getApplicationEntityFromApiKey(apikey);


        if(applicationEntity == null)
        {
            return ResponseEntity.notFound().build();
        }

        //TODO
        return ResponseEntity.ok().build();
    }

}
