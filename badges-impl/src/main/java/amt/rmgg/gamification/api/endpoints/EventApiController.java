package amt.rmgg.gamification.api.endpoints;

import amt.rmgg.gamification.api.EventsApi;
import amt.rmgg.gamification.api.model.Badge;
import amt.rmgg.gamification.api.model.Event;
import amt.rmgg.gamification.api.util.ApiKeyManager;
import amt.rmgg.gamification.api.util.EventProcessorService;
import amt.rmgg.gamification.entities.ApplicationEntity;
import amt.rmgg.gamification.entities.BadgeEntity;
import amt.rmgg.gamification.entities.EventEntity;
import amt.rmgg.gamification.repositories.EventRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.InvalidObjectException;

public class EventApiController implements EventsApi {

    @Autowired
    private ApiKeyManager apiKeyManager;

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    EventProcessorService eventProcessorService;

    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Badge> sendEvent(@ApiParam @Valid @RequestBody Event event)
    {
        String apikey = httpServletRequest.getHeader("x-api-key");
        ApplicationEntity applicationEntity = apiKeyManager.getApplicationEntityFromApiKey(apikey);


        if(applicationEntity == null)
        {
            return ResponseEntity.notFound().build();
        }

        try
        {
            BadgeEntity awardedBadge = eventProcessorService.process(event, apikey);
            EventEntity newEventEntity = toEventEntity(event);
            eventRepository.save(newEventEntity);
            return ResponseEntity.ok(BadgesApiController.toBadge(awardedBadge));
        }
        catch (InvalidObjectException e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    private EventEntity toEventEntity(Event event)
    {
        EventEntity entity = new EventEntity();
        entity.setName(event.getName());
        entity.setDescription(event.getDescription());
        entity.setRank(event.getRank());
        entity.setUserId(event.getUserid());
        return  entity;
    }
}
