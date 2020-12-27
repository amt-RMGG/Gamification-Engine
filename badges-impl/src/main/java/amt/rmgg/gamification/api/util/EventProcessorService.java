package amt.rmgg.gamification.api.util;

import amt.rmgg.gamification.api.model.Event;
import amt.rmgg.gamification.entities.*;
import amt.rmgg.gamification.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InvalidObjectException;
import java.security.InvalidParameterException;
import java.util.List;
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

    EventProcessorService() {
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
                    List<EventCountEntity> eventCounters = eventCountRepository.findByEventTypeEntityAndUsername(eventTypeEntity, event.getUsername());
                    if(eventCounters.isEmpty())
                    {
                        EventCountEntity newCounter = new EventCountEntity();
                        newCounter.setEventTypeEntity(eventTypeEntity);
                        newCounter.setUsername(event.getUsername());
                        eventCountRepository.save(newCounter);
                        eventCounters = eventCountRepository.findByEventTypeEntityAndUsername(eventTypeEntity, event.getUsername());
                    }
                    for(EventCountEntity counter : eventCounters)
                    {
                        counter.setCount(counter.getCount()+1);
                        eventCountRepository.save(counter);
                        if(counter.getCount()==ruleEntity.getThreshold())
                        {
                            BadgeEntity awardedBadgeEntity = ruleEntity.getBadge();
                            UserEntity userEntity = userRepository.findById(event.getUsername()).get();
                            awardedBadgeEntity.getUsers().add(userEntity);
                            badgeRepository.save(awardedBadgeEntity);
                            userEntity.getBadges().add(awardedBadgeEntity);
                            userRepository.save(userEntity);
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
