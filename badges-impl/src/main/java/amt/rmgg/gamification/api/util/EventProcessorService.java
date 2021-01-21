package amt.rmgg.gamification.api.util;

import amt.rmgg.gamification.api.model.Event;
import amt.rmgg.gamification.entities.*;
import amt.rmgg.gamification.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    EventRepository eventRepository;

    @Autowired
    BadgeRepository badgeRepository;

    @Autowired
    UserRepository userRepository;

    public BadgeEntity process(EventEntity event) throws InvalidObjectException {

        eventRepository.save(event);
        EventTypeEntity eventTypeEntity = event.getEventTypeEntity();
        UserEntity userEntity = event.getUserEntity();
        List<RuleEntity> rules  = ruleRepository.findByEventType(eventTypeEntity);
        for(RuleEntity ruleEntity : rules)
        {
            long noOfEvents = eventRepository.countEventEntitiesByUserEntityAndEventTypeEntity(userEntity, eventTypeEntity);
            if(noOfEvents==ruleEntity.getThreshold())
            {
                BadgeEntity awardedBadgeEntity = ruleEntity.getBadge();
                awardedBadgeEntity.getUsers().add(userEntity);
                badgeRepository.save(awardedBadgeEntity);
                userEntity.getBadges().add(awardedBadgeEntity);
                userRepository.save(userEntity);
                // !!! WARNING : Can only allow for a single badge to be awarded at a time !!!

                return awardedBadgeEntity;
            }
        }

        return null;//TODO : Behaviour if no awarded badge ???
    }
}
