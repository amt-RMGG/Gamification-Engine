package amt.rmgg.gamification.api.endpoints;

import amt.rmgg.gamification.api.EventCountersApi;
import amt.rmgg.gamification.api.model.EventCounter;
import amt.rmgg.gamification.api.util.ApiKeyManager;
import amt.rmgg.gamification.entities.ApplicationEntity;
import amt.rmgg.gamification.entities.EventCounterEntity;
import amt.rmgg.gamification.repositories.AppRepository;
import amt.rmgg.gamification.repositories.EventCounterRepository;
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
public class EventCountersApiController implements EventCountersApi {
    @Autowired
    private ApiKeyManager apiKeyManager;
    @Autowired
    AppRepository appRepository;
    @Autowired
    HttpServletRequest httpServletRequest;
    @Autowired
    EventCounterRepository eventCounterRepository;

    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EventCounter> createEventCounter(@ApiParam(value = "", required = true) @Valid @RequestBody EventCounter eventCounter ) {

        String apikey = httpServletRequest.getHeader("x-api-key");
        ApplicationEntity applicationEntity = apiKeyManager.getApplicationEntityFromApiKey(apikey);

        if(applicationEntity == null){
            return ResponseEntity.notFound().build();
        }

        EventCounterEntity newEventCounterEntity = toEventCounterEntity(eventCounter);
        applicationEntity.getEventCounters().add(newEventCounterEntity);

        eventCounterRepository.save(newEventCounterEntity);

        try {
            return ResponseEntity.created(new URI("/eventCounters/" + newEventCounterEntity.getId())).body(toEventCounter(newEventCounterEntity));
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    public ResponseEntity<List<EventCounter>> getEventCounters() {
        String apikey = httpServletRequest.getHeader("x-api-key");
        ApplicationEntity applicationEntity = apiKeyManager.getApplicationEntityFromApiKey(apikey);

        return ResponseEntity.ok(
                applicationEntity
                        .getEventCounters()
                        .stream()
                        .map(EventCountersApiController::toEventCounter)
                        .collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<EventCounter> getEventCounter(@ApiParam(value = "",required=true) @PathVariable("id") Integer id) {
        String apikey = httpServletRequest.getHeader("x-api-key");
        ApplicationEntity applicationEntity = apiKeyManager.getApplicationEntityFromApiKey(apikey);

        List<EventCounterEntity> eventCounters = applicationEntity
                .getEventCounters()
                .stream()
                .filter(et -> et.getId() == id)
                .limit(1)
                .collect(Collectors.toList());

        if(eventCounters.size() != 1){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        EventCounterEntity eventCounterEntity = eventCounters.get(0);
        return ResponseEntity.ok(toEventCounter(eventCounterEntity));
    }


    public static EventCounterEntity toEventCounterEntity(EventCounter eventCounter) {
        EventCounterEntity entity = new EventCounterEntity();
        entity.setName(eventCounter.getName());
        entity.setInitialValue(entity.getInitialValue());
        return entity;
    }


    public static EventCounter toEventCounter(EventCounterEntity entity){
        EventCounter eventCounter = new EventCounter();
        eventCounter.setId(entity.getId());
        eventCounter.setName(entity.getName());
        eventCounter.setInitialValue(entity.getInitialValue());
        return eventCounter;
    }

}
