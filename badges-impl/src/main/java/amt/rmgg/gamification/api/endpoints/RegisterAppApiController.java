package amt.rmgg.gamification.api.endpoints;

import amt.rmgg.gamification.api.ApplicationsApi;
import amt.rmgg.gamification.api.model.ApiKey;
import amt.rmgg.gamification.api.model.Application;
import amt.rmgg.gamification.api.util.ApiKeyManager;
import amt.rmgg.gamification.entities.ApplicationEntity;
import amt.rmgg.gamification.repositories.AppRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;
import java.util.UUID;

@Controller
public class RegisterAppApiController implements ApplicationsApi {

    @Autowired
    private ApiKeyManager apiKeyManager;

    @Autowired
    private AppRepository appRepository;

    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiKey> registerApp(@ApiParam("") @Valid @RequestBody(required = false) Application application)
    {
        UUID uuid = UUID.randomUUID();
        String hashedApiKey = apiKeyManager.hashKey(uuid.toString());
        ApplicationEntity applicationEntity = new ApplicationEntity();

        applicationEntity.setName(application.getName());
        applicationEntity.setDescription(application.getDescription());
        applicationEntity.setApikey(hashedApiKey);

        appRepository.save(applicationEntity);

        ApiKey apiKey = new ApiKey();
        apiKey.setUuid(uuid.toString());

        return ResponseEntity.ok(apiKey); //return 200 and not 201 because the created application is not accessible
    }
}
