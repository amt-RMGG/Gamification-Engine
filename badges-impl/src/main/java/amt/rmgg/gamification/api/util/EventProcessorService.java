package amt.rmgg.gamification.api.util;

import amt.rmgg.gamification.api.model.Event;
import amt.rmgg.gamification.entities.BadgeEntity;
import org.springframework.stereotype.Component;

import java.io.InvalidObjectException;
import java.util.HashMap;
import java.util.Map;

@Component
public class EventProcessorService {
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
        Rules applicationRules;
        if(ruleBook.containsKey(applicationKey))
        {
            applicationRules = ruleBook.get(applicationKey);
        }
        else
        {
            applicationRules = ruleBook.get("default");
        }

        //TODO FIXME (remplacé pour que ça compile, on en a plus besoin de toute façon)
        BadgeEntity awardedBadge = applicationRules.rules.get(/*event.getEventRank()*/"Silver");
        if(awardedBadge == null)
        {
            //TODO FIXME (remplacé pour que ça compile, on en a plus besoin de toute façon)
            throw new InvalidObjectException("Unknown event rank : "/*+event.getEventRank()*/);
        }

        return awardedBadge;
    }
}
