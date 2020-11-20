package amt.rmgg.gamification.api.endpoints;

import amt.rmgg.gamification.api.util.ApiKeyManager;
import amt.rmgg.gamification.entities.ApplicationEntity;
import amt.rmgg.gamification.entities.BadgeEntity;
import amt.rmgg.gamification.repositories.AppRepository;
import amt.rmgg.gamification.repositories.BadgeRepository;
import amt.rmgg.gamification.api.BadgesApi;
import amt.rmgg.gamification.api.model.Badge;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.net.ssl.KeyManager;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
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
    public ResponseEntity<Void> createBadge(@ApiParam(value = "", required = true) @Valid @RequestBody Badge badge ) {

        String apikey = httpServletRequest.getHeader("x-api-key");
        ApplicationEntity applicationEntity = apiKeyManager.getApplicationEntityFromApiKey(apikey);


        if(applicationEntity == null){
            return ResponseEntity.notFound().build();
        }

        BadgeEntity newBadgeEntity = toBadgeEntity(badge);
        applicationEntity.getBadges().add(newBadgeEntity);

        badgeRepository.save(newBadgeEntity);

        Long id = newBadgeEntity.getId();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newBadgeEntity.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    public ResponseEntity<List<Badge>> getBadges() {
        String apikey = httpServletRequest.getHeader("x-api-key");
        ApplicationEntity applicationEntity = apiKeyManager.getApplicationEntityFromApiKey(apikey);

        return ResponseEntity.ok(
                applicationEntity
                        .getBadges()
                        .stream()
                        .map(this::toBadge)
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

    private BadgeEntity toBadgeEntity(Badge badge) {
        BadgeEntity entity = new BadgeEntity();
        entity.setName(badge.getName());
        entity.setExperienceValue(badge.getExperienceValue());
        return entity;
    }

    private Badge toBadge(BadgeEntity entity) {
        Badge badge = new Badge();
        badge.setName(entity.getName());
        badge.setExperienceValue(entity.getExperienceValue());
        return badge;
    }

}
