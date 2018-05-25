package backend.googleSignIn;
import backend.entity.AppUser;
import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;

//import static javafx.application.Platform.exit;

public class SignUpGoogle {
    private static final String CLIENT_SECRET_FILE = "src/main/resources/clientSecret.json";
    private static final String redirectUrl="http://localhost:8080/token";
public static AppUser getGoogleUser(String authCode){
    GoogleClientSecrets clientSecrets = null;

    {
        try {
            clientSecrets = GoogleClientSecrets.load(
                    JacksonFactory.getDefaultInstance(), new FileReader(CLIENT_SECRET_FILE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    GoogleTokenResponse tokenResponse=null;

    {
        try {
            tokenResponse = new GoogleAuthorizationCodeTokenRequest(
                    new NetHttpTransport(),
                    JacksonFactory.getDefaultInstance(),
                    "https://www.googleapis.com/oauth2/v4/token",
                    clientSecrets.getDetails().getClientId(),
                    clientSecrets.getDetails().getClientSecret(),
                    authCode,
                    redirectUrl)  // Specify the same redirect URI that you use with your web
                    //                    // app. If you don't have a web version of your app, you can
                    // specify an empty string.
                    .execute();
        } catch (IOException e) {
           e.printStackTrace();
            return null;
        }
    }

    String accessToken = tokenResponse.getAccessToken();
    String refreshToken = tokenResponse.getRefreshToken();
    // Use access token to call API
    GoogleCredential credential = new GoogleCredential().setAccessToken(accessToken);
    // Get profile info from ID token
    GoogleIdToken idToken = null;

    {
        try {
            idToken = tokenResponse.parseIdToken();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    GoogleIdToken.Payload payload = idToken.getPayload();
    String userId = payload.getSubject();  // Use this value as a key to identify a user.
    String email = payload.getEmail();
    boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
    String name = (String) payload.get("name");
    String pictureUrl = (String) payload.get("picture");
    String locale = (String) payload.get("locale");
    String familyName = (String) payload.get("family_name");
    String givenName = (String) payload.get("given_name");
    AppUser user=new AppUser(userId,email,"123",name,accessToken,refreshToken);
return user;
}

}
