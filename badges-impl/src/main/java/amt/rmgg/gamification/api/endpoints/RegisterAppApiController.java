package amt.rmgg.gamification.api.endpoints;

import amt.rmgg.gamification.api.ApplicationsApi;
import amt.rmgg.gamification.api.model.Application;
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
    AppRepository appRepository;

    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> registerApp(@ApiParam("") @Valid @RequestBody(required = false) Application application)
    {
        UUID uuid = null;//TODO : Generer l'uuid
        String encodedUUID = null;//TODO : Hasher l'UUID
        ApplicationEntity applicationEntity = null;//TODO : Create application entity
        appRepository.save(applicationEntity);

        return ResponseEntity.ok(uuid);
    }
}
