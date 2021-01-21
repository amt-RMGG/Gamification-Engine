package amt.rmgg.gamification.api.endpoints;

import amt.rmgg.gamification.api.RulesApi;
import amt.rmgg.gamification.api.model.Rule;
import amt.rmgg.gamification.api.util.ApiKeyManager;
import amt.rmgg.gamification.entities.ApplicationEntity;
import amt.rmgg.gamification.entities.BadgeEntity;
import amt.rmgg.gamification.entities.EventTypeEntity;
import amt.rmgg.gamification.entities.RuleEntity;
import amt.rmgg.gamification.repositories.BadgeRepository;
import amt.rmgg.gamification.repositories.EventTypeRepository;
import amt.rmgg.gamification.repositories.RuleRepository;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class RulesApiController implements RulesApi {
    @Autowired
    private ApiKeyManager apiKeyManager;
    @Autowired
    RuleRepository ruleRepository;
    @Autowired
    BadgeRepository badgeRepository;
    @Autowired
    HttpServletRequest httpServletRequest;
    @Autowired
    EventTypeRepository eventTypeRepository;

    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Rule> createRule(@ApiParam(value = "", required = true) @Valid @RequestBody Rule rule ) {

        String apikey = httpServletRequest.getHeader("x-api-key");
        ApplicationEntity applicationEntity = apiKeyManager.getApplicationEntityFromApiKey(apikey);


        if(applicationEntity == null){
            return ResponseEntity.notFound().build();
        }

        Optional<BadgeEntity> badgeEntity = badgeRepository.findById((long)rule.getBadgeId());
        if(badgeEntity.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Optional<EventTypeEntity> eventTypeEntity = eventTypeRepository.findById(rule.getEventTypeId());
        if(eventTypeEntity.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        RuleEntity newRuleEntity = toRuleEntity(rule, badgeEntity.get(), eventTypeEntity.get());
        applicationEntity.getRules().add(newRuleEntity);

        ruleRepository.save(newRuleEntity);

        try {
            return ResponseEntity.created(new URI("/rules/" + newRuleEntity.getId())).body(toRule(newRuleEntity));
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    public ResponseEntity<List<Rule>> getRules() {
        String apikey = httpServletRequest.getHeader("x-api-key");
        ApplicationEntity applicationEntity = apiKeyManager.getApplicationEntityFromApiKey(apikey);

        return ResponseEntity.ok(
                applicationEntity
                        .getRules()
                        .stream()
                        .map(RulesApiController::toRule)
                        .collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<Rule> getRule(@ApiParam(value = "",required=true) @PathVariable("id") Integer id) {
        String apikey = httpServletRequest.getHeader("x-api-key");
        ApplicationEntity applicationEntity = apiKeyManager.getApplicationEntityFromApiKey(apikey);

        List<RuleEntity> rules = applicationEntity
                .getRules()
                .stream()
                .filter(r -> r.getId() == id)
                .limit(1)
                .collect(Collectors.toList());

        if(rules.size() != 1){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        RuleEntity ruleEntity = rules.get(0);
        return ResponseEntity.ok(toRule(ruleEntity));
    }


    public static RuleEntity toRuleEntity(Rule rule, BadgeEntity badge, EventTypeEntity eventType) {
        RuleEntity entity = new RuleEntity();
        entity.setEventType(eventType);
        entity.setThreshold(rule.getThreshold());
        entity.setBadge(badge);
        return entity;
    }


    public static Rule toRule(RuleEntity entity){
        Rule rule = new Rule();
        rule.setId(entity.getId());
        rule.setEventTypeId(entity.getEventType().getId());
        rule.setThreshold(entity.getThreshold());
        rule.setBadgeId((int) entity.getBadge().getId());
        return rule;
    }

}
