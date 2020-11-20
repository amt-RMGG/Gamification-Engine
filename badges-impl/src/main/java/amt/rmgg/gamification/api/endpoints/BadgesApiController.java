package amt.rmgg.gamification.api.endpoints;

import amt.rmgg.gamification.entities.ApplicationEntity;
import amt.rmgg.gamification.entities.BadgeEntity;
import amt.rmgg.gamification.repositories.BadgeRepository;
import amt.rmgg.gamification.api.BadgesApi;
import amt.rmgg.gamification.api.model.Badge;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.metamodel.Metamodel;
import javax.swing.text.html.parser.Entity;
import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class BadgesApiController implements BadgesApi {

    @Autowired
    BadgeRepository badgeRepository;

    private ApplicationEntity applicationEntity = new ApplicationEntity();

    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createBadge(@ApiParam(value = "", required = true) @Valid @RequestBody Badge badge) {
        BadgeEntity newBadgeEntity = toBadgeEntity(badge);
        applicationEntity.setApikey("abc");
        applicationEntity.setName("my name");
        applicationEntity.setDescription("desc");
        newBadgeEntity.setApplication(applicationEntity);

        badgeRepository.save(newBadgeEntity);


        Long id = newBadgeEntity.getId();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newBadgeEntity.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    public ResponseEntity<List<Badge>> getBadges() {
        List<Badge> badges = new ArrayList<>();
        for (BadgeEntity badgeEntity : badgeRepository.findAll()) {
            badges.add(toBadge(badgeEntity));
        }
        return ResponseEntity.ok(badges);
    }

    @Override
    public ResponseEntity<Badge> getBadge(@ApiParam(value = "",required=true) @PathVariable("id") Integer id) {
        BadgeEntity existingBadgeEntity = badgeRepository.findById(Long.valueOf(id)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.ok(toBadge(existingBadgeEntity));
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
