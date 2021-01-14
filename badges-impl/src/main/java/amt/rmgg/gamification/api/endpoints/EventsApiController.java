package amt.rmgg.gamification.api.endpoints;

import amt.rmgg.gamification.api.EventsApi;
import amt.rmgg.gamification.api.model.Badge;
import amt.rmgg.gamification.api.model.Event;
import amt.rmgg.gamification.api.util.ApiKeyManager;
import amt.rmgg.gamification.api.util.EventProcessorService;
import amt.rmgg.gamification.entities.*;
import amt.rmgg.gamification.repositories.AppRepository;
import amt.rmgg.gamification.repositories.EventTypeRepository;
import amt.rmgg.gamification.repositories.UserRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.InvalidObjectException;
import java.sql.Timestamp;
import java.util.Date;

@Controller
public class EventsApiController implements EventsApi {

    @Autowired
    private ApiKeyManager apiKeyManager;

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    AppRepository appRepository;

    @Autowired
    EventProcessorService eventProcessorService;

    @Autowired
    EventTypeRepository eventTypeRepository;

    @Autowired
    UserRepository userRepository;

    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Badge> sendEvent(@ApiParam @Valid @RequestBody Event event)
    {
        String apikey = httpServletRequest.getHeader("x-api-key");

        ApplicationEntity applicationEntity = apiKeyManager.getApplicationEntityFromApiKey(apikey);

        try {

            if(userRepository.findByUsername(event.getUsername()).isEmpty())
            {
                UserEntity newUser = new UserEntity();
                if(event.getUsername()==null) {
                    throw new InvalidObjectException("Event has no user");
                }
                newUser.setUsername(event.getUsername());
                userRepository.save(newUser);
                applicationEntity.getUsers().add(newUser);
                appRepository.save(applicationEntity);
            }

            BadgeEntity awardedBadgeEntity = eventProcessorService.process(toEventEntity(event));
            if(awardedBadgeEntity == null)
            {
                return ResponseEntity.ok(null);
            }
            Badge awardedBadge = BadgesApiController.toBadge(awardedBadgeEntity);
            return ResponseEntity.ok(awardedBadge);
        }
        catch (InvalidObjectException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request params", e);
        }

    }

    public EventEntity toEventEntity(Event event) throws InvalidObjectException {
        EventEntity eventEntity = new EventEntity();
        eventEntity.setUserEntity(userRepository.findByUsername(event.getUsername())
                .orElseThrow(() -> new InvalidObjectException("Unknown username")));
        eventEntity.setEventTypeEntity(eventTypeRepository.findById(event.getEventTypeId())
                .orElseThrow(() -> new InvalidObjectException("Unknown event type")));
        eventEntity.setTimestamp(new Date());
        return eventEntity;
    }
}
