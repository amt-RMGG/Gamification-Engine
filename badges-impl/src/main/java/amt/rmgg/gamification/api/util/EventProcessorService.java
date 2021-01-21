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
    EventCounterRepository eventCounterRepository;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    BadgeRepository badgeRepository;

    @Autowired
    UserRepository userRepository;

    public BadgeEntity process(EventEntity event) throws InvalidObjectException {

        eventRepository.save(event);
        EventCounterEntity eventCounterEntity = event.getEventCounterEntity();
        UserEntity userEntity = event.getUserEntity();
        List<RuleEntity> rules  = ruleRepository.findByEventCounter(eventCounterEntity);
        for(RuleEntity ruleEntity : rules)
        {
            long noOfEvents = eventRepository.countEventEntitiesByUserEntityAndEventCounterEntity(userEntity, eventCounterEntity);
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
