package amt.rmgg.gamification.api.util;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class ApiKeyManager {
    // TODO : Pour l'instant comme ça, après j'irai taper
    String keys[] = {"abc123", "s3cr3t", "myapiKey"};

    public boolean isKeyValid(String _key){
        for (String key : keys){
            if(key.equals(_key))
                return true;
        }
        return false;
    }

    public static String hashKey(String _key) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(_key.getBytes());
        String stringHash = new String(messageDigest.digest());
        return stringHash;
    }
}
