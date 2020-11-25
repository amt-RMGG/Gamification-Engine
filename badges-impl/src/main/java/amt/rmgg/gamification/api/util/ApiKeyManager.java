package amt.rmgg.gamification.api.util;

import amt.rmgg.gamification.entities.ApplicationEntity;
import amt.rmgg.gamification.repositories.AppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Optional;

@Component
public class ApiKeyManager {

    @Autowired
    private AppRepository appRepository;

    public boolean isKeyValid(String _key){
        return getApplicationEntityFromApiKey(_key) != null;
    }

    public String hashKey(String _key){
        MessageDigest messageDigest = null;
        try{
            messageDigest = MessageDigest.getInstance("SHA-256");
        }catch (NoSuchAlgorithmException e){
            System.out.println("ooops");
            return null;
        }
        messageDigest.update(_key.getBytes());
        String stringHash = new String(messageDigest.digest());
        return stringHash;
    }

    public ApplicationEntity getApplicationEntityFromApiKey(String apiKey){
        Optional<ApplicationEntity> applicationEntityOptional = appRepository.findById(hashKey(apiKey));
        if(applicationEntityOptional.isPresent()) {
            return applicationEntityOptional.get();
        }else{
            return null;
        }
    }

}
