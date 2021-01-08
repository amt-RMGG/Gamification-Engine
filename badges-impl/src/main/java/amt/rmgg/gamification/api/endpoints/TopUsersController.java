package amt.rmgg.gamification.api.endpoints;

import amt.rmgg.gamification.api.TopApi;
import amt.rmgg.gamification.api.model.User;
import amt.rmgg.gamification.api.util.ApiKeyManager;
import amt.rmgg.gamification.entities.ApplicationEntity;
import amt.rmgg.gamification.entities.BadgeEntity;
import amt.rmgg.gamification.entities.UserEntity;
import amt.rmgg.gamification.repositories.AppRepository;
import amt.rmgg.gamification.repositories.BadgeRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class TopUsersController implements TopApi {
    @Autowired
    private ApiKeyManager apiKeyManager;
    @Autowired
    BadgeRepository badgeRepository;
    @Autowired
    AppRepository appRepository;
    @Autowired
    HttpServletRequest httpServletRequest;

    @Override
    public ResponseEntity<List<User>> getTopN(@ApiParam(value = "",required=true) @PathVariable("n") Integer n) {
        String apikey = httpServletRequest.getHeader("x-api-key");
        ApplicationEntity applicationEntity = apiKeyManager.getApplicationEntityFromApiKey(apikey);

        List<User> topUsers = applicationEntity
                .getUsers()
                .stream()
                .map(TopUsersController::toUser)
                .sorted((u1, u2) -> Long.compare(u2.getExperienceValue(), u1.getExperienceValue()))
                .limit(n)
                .collect(Collectors.toList());

        return ResponseEntity.ok(topUsers);
    }

    public static User toUser(UserEntity entity) {
        User user = new User();
        user.setUsername(entity.getUsername());
        int total = 0;
        for(BadgeEntity b : entity.getBadges()){
            total += b.getExperienceValue();
        }
        user.setExperienceValue((long) total);
        return user;
    }

}
