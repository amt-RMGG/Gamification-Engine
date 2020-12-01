package amt.rmgg.gamification.api.endpoints;

import amt.rmgg.gamification.api.BadgesApi;
import amt.rmgg.gamification.api.model.Badge;
import amt.rmgg.gamification.api.util.ApiKeyManager;
import amt.rmgg.gamification.entities.ApplicationEntity;
import amt.rmgg.gamification.entities.BadgeEntity;
import amt.rmgg.gamification.repositories.AppRepository;
import amt.rmgg.gamification.repositories.BadgeRepository;
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
public class BadgesApiController implements BadgesApi {
    @Autowired
    private ApiKeyManager apiKeyManager;
    @Autowired
    BadgeRepository badgeRepository;
    @Autowired
    AppRepository appRepository;
    @Autowired
    HttpServletRequest httpServletRequest;

    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Badge> createBadge(@ApiParam(value = "", required = true) @Valid @RequestBody Badge badge ) {

        String apikey = httpServletRequest.getHeader("x-api-key");
        ApplicationEntity applicationEntity = apiKeyManager.getApplicationEntityFromApiKey(apikey);


        if(applicationEntity == null){
            return ResponseEntity.notFound().build();
        }

        BadgeEntity newBadgeEntity;
        try{
            newBadgeEntity = toBadgeEntity(badge);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

        applicationEntity.getBadges().add(newBadgeEntity);

        badgeRepository.save(newBadgeEntity);

      /*  Long id = newBadgeEntity.getId();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newBadgeEntity.getId()).toUri();
*/
        try {
            return ResponseEntity.created(new URI("/badges/" + newBadgeEntity.getId())).body(badge);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    public ResponseEntity<List<Badge>> getBadges() {
        String apikey = httpServletRequest.getHeader("x-api-key");
        ApplicationEntity applicationEntity = apiKeyManager.getApplicationEntityFromApiKey(apikey);

        return ResponseEntity.ok(
                applicationEntity
                        .getBadges()
                        .stream()
                        .map(BadgesApiController::toBadge)
                        .collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<Badge> getBadge(@ApiParam(value = "",required=true) @PathVariable("id") Integer id) {
        String apikey = httpServletRequest.getHeader("x-api-key");
        ApplicationEntity applicationEntity = apiKeyManager.getApplicationEntityFromApiKey(apikey);

        List<BadgeEntity> badges = applicationEntity
                .getBadges()
                .stream()
                .filter(b -> b.getId() == id)
                .limit(1)
                .collect(Collectors.toList());

        if(badges.size() != 1){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        BadgeEntity badge = badges.get(0);
        return ResponseEntity.ok(toBadge(badge));
    }

    public static BadgeEntity toBadgeEntity(Badge badge) {
        BadgeEntity entity = new BadgeEntity();
        entity.setName(badge.getName());
        entity.setExperienceValue(badge.getExperienceValue());
        return entity;
    }

    public static Badge toBadge(BadgeEntity entity) {
        Badge badge = new Badge();
        badge.setName(entity.getName());
        badge.setExperienceValue(entity.getExperienceValue());
        return badge;
    }

}
