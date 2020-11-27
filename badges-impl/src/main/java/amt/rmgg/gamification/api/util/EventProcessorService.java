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


        /**
         * TODO : Selon Liechti, on fait faux ici . C'est pas a nous de définir quel event trigger quelle regle et quelle récompense
         * Nous on s'occupe juste de :
         *  - Stocker des type de badges
         *  - Stocker des evenements, qui serait simplement composé de : une action, un user id
         *  Notre "client" (donc celui qui utilise cette api), il pourra nous demander par ex. les choses suivantes :
         *  - Donne moi tout mes badges
         *  - Donne moi un de mes badges
         *  - Attribue ce badge au user X (donc une nouvelle table "reward" (ou autre chose))
         *  - Donne moi tout les badges atttribué au user X
         *  - Donne moi tout les events concernant le user X
         *  - Donne moi le user qui a le plus fait "ce type d'event" la
         *
         *   En gros c'est le client qui va gèrer ses "règles".
         *   Ce qu'il fera c'est par ex. :
         *   - A chaque upvote sur StackUnderflow, il envoi un nouvel event à notre API
         *   - Dans ses règles à lui, il check grace a notre api le nombre d'event "upvote" qu'on a et qui concerne le user X
         *   - Selon ce nombre, il choisira si il veut attribuer un badge ou non au user, et si il le fait, il nous demandera de lui rajouter ce badge
         *   (dans la table "reward" par ex.)
         */
        public Rules()
        {
            rules = new HashMap<>();
            BadgeEntity bronzeBadge = new BadgeEntity();
            bronzeBadge.setName("Bronze badge");
            bronzeBadge.setExperienceValue(2);
            rules.put("bronze", bronzeBadge);

            BadgeEntity silverBadge = new BadgeEntity();
            silverBadge.setName("Silver badge");
            silverBadge.setExperienceValue(5);
            rules.put("silver", silverBadge);

            BadgeEntity goldBadge = new BadgeEntity();
            goldBadge.setName("Gold badge");
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

        BadgeEntity awardedBadge = applicationRules.rules.get(event.getBadgeRank());
        if(awardedBadge == null)
        {
            throw new InvalidObjectException("Unknown event rank : "+event.getBadgeRank());
        }

        return awardedBadge;
    }
}
