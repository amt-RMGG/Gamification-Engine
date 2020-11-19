package amt.rmgg.gamification.api.util;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class ApiKeyManager {
    // TODO : Pour l'instant comme ça, après j'irai taper dans la DB
    static private ArrayList<String> keys = new ArrayList<>();

    public static boolean isKeyValid(String _key){
        for (String key : keys){
            if(key.equals(_key))
                return true;
        }
        return false;
    }

    // Temporaire!
    public static void addKey(String _key){
        keys.add(_key);
    }

    public static String hashKey(String _key){
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
}
