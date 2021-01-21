package amt.rmgg.gamification.api.endpoints;

import amt.rmgg.gamification.api.EventTypesApi;
import amt.rmgg.gamification.api.model.EventType;
import amt.rmgg.gamification.api.util.ApiKeyManager;
import amt.rmgg.gamification.entities.ApplicationEntity;
import amt.rmgg.gamification.entities.EventTypeEntity;
import amt.rmgg.gamification.repositories.AppRepository;
import amt.rmgg.gamification.repositories.EventTypeRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class EventTypesApiController implements EventTypesApi {
    @Autowired
    private ApiKeyManager apiKeyManager;
    @Autowired
    AppRepository appRepository;
    @Autowired
    HttpServletRequest httpServletRequest;
    @Autowired
    EventTypeRepository eventTypeRepository;

    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EventType> createEventType(@ApiParam(value = "", required = true) @Valid @RequestBody EventType eventType ) {

        String apikey = httpServletRequest.getHeader("x-api-key");
        ApplicationEntity applicationEntity = apiKeyManager.getApplicationEntityFromApiKey(apikey);

        if(applicationEntity == null){
            return ResponseEntity.notFound().build();
        }

        EventTypeEntity newEventTypeEntity = toEventTypeEntity(eventType);
        applicationEntity.getEventTypes().add(newEventTypeEntity);

        eventTypeRepository.save(newEventTypeEntity);

        try {
            return ResponseEntity.created(new URI("/eventTypes/" + newEventTypeEntity.getId())).body(toEventType(newEventTypeEntity));
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    public ResponseEntity<List<EventType>> getEventTypes() {
        String apikey = httpServletRequest.getHeader("x-api-key");
        ApplicationEntity applicationEntity = apiKeyManager.getApplicationEntityFromApiKey(apikey);

        return ResponseEntity.ok(
                applicationEntity
                        .getEventTypes()
                        .stream()
                        .map(EventTypesApiController::toEventType)
                        .collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<EventType> getEventType(@ApiParam(value = "",required=true) @PathVariable("id") Integer id) {
        String apikey = httpServletRequest.getHeader("x-api-key");
        ApplicationEntity applicationEntity = apiKeyManager.getApplicationEntityFromApiKey(apikey);

        List<EventTypeEntity> eventTypes = applicationEntity
                .getEventTypes()
                .stream()
                .filter(et -> et.getId() == id)
                .limit(1)
                .collect(Collectors.toList());

        if(eventTypes.size() != 1){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        EventTypeEntity eventTypeEntity = eventTypes.get(0);
        return ResponseEntity.ok(toEventType(eventTypeEntity));
    }


    public static EventTypeEntity toEventTypeEntity(EventType eventType) {
        EventTypeEntity entity = new EventTypeEntity();
        entity.setName(eventType.getName());
        entity.setInitialValue(entity.getInitialValue());
        return entity;
    }


    public static EventType toEventType(EventTypeEntity entity){
        EventType eventType = new EventType();
        eventType.setId(entity.getId());
        eventType.setName(entity.getName());
        eventType.setInitialValue(entity.getInitialValue());
        return eventType;
    }

}
