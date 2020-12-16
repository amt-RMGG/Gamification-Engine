package amt.rmgg.gamification.api.util;

import amt.rmgg.gamification.api.model.Badge;
import amt.rmgg.gamification.api.model.Event;
import amt.rmgg.gamification.api.model.EventType;
import amt.rmgg.gamification.api.model.Rule;
import amt.rmgg.gamification.entities.BadgeEntity;
import amt.rmgg.gamification.entities.EventCountEntity;
import amt.rmgg.gamification.entities.EventTypeEntity;
import amt.rmgg.gamification.entities.RuleEntity;
import amt.rmgg.gamification.repositories.AppRepository;
import amt.rmgg.gamification.repositories.EventCountRepository;
import amt.rmgg.gamification.repositories.EventTypeRepository;
import amt.rmgg.gamification.repositories.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.io.InvalidObjectException;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class EventProcessorService {

    @Autowired
    ApiKeyManager apiKeyManager;

    @Autowired
    RuleRepository ruleRepository;

    @Autowired
    EventTypeRepository eventTypeRepository;

    @Autowired
    EventCountRepository eventCountRepository;

    //TODO : Temporary default rules definition, needs to be user-defined and stored in database
    private static class Rules {
        private Map<String, BadgeEntity> rules;

        public Rules()
        {
            rules = new HashMap<>();
            BadgeEntity bronzeBadge = new BadgeEntity();
            bronzeBadge.setExperienceValue(2);
            rules.put("bronze", bronzeBadge);

            BadgeEntity silverBadge = new BadgeEntity();
            silverBadge.setExperienceValue(5);
            rules.put("silver", silverBadge);

            BadgeEntity goldBadge = new BadgeEntity();
            goldBadge.setExperienceValue(10);
            rules.put("gold", goldBadge);
        }


    }

    private Map<String, Rules> ruleBook;

    EventProcessorService() {
        ruleBook = new HashMap<>();
        ruleBook.put("default", new Rules());
    }

    public BadgeEntity process(Event event, String applicationKey) throws InvalidObjectException {
        if(apiKeyManager.isKeyValid(applicationKey))
        {
            Optional<EventTypeEntity> eventTypeEntityOptional = eventTypeRepository.findById(event.getEventTypeId());
            if(eventTypeEntityOptional.isEmpty())
            {
                throw new InvalidParameterException("Invalid event type");
            }
            else
            {
                EventTypeEntity eventTypeEntity = eventTypeEntityOptional.get();
                List<RuleEntity> rules  = ruleRepository.findByEventType(eventTypeEntity);
                for(RuleEntity ruleEntity : rules)
                {
                    List<EventCountEntity> eventCounters = eventCountRepository.findByEventTypeEntityAndUserId(eventTypeEntity, event.getUserid());
                    if(eventCounters.isEmpty())
                    {
                        EventCountEntity newCounter = new EventCountEntity();
                        newCounter.setEventTypeEntity(eventTypeEntity);
                        newCounter.setUserId(event.getUserid());
                        eventCountRepository.save(newCounter);
                        eventCounters = eventCountRepository.findByEventTypeEntityAndUserId(eventTypeEntity, event.getUserid());
                    }
                    for(EventCountEntity counter : eventCounters)
                    {
                        counter.setCount(counter.getCount()+1);
                        eventCountRepository.save(counter);
                        if(counter.getCount()==ruleEntity.getThreshold())
                        {
                            BadgeEntity awardedBadgeEntity = ruleEntity.getBadge();
                            // !!! WARNING : Can only allow for a single badge to be awarded at a time !!!

                            return awardedBadgeEntity;
                        }
                    }
                }
            }
            return null;//TODO : Behaviour if no awarded badge ???
        }
        else
        {
            throw new InvalidParameterException("Invalid API key");
        }
    }
}
