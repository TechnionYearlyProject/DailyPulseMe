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


public class SignUpGoogle {
  //  private static final String CLIENT_SECRET_FILE = "src/main/resources/clientSecret.json";
    private static final String redirectUrl="https://dailypulse.azurewebsites.net/googleauth";
    public static AppUser getGoogleUser(String authCode)throws Exception{
        System.out.println("authCode : "+authCode);
     /*   GoogleClientSecrets clientSecrets = null;
        {
            try {
                clientSecrets = GoogleClientSecrets.load(
                        JacksonFactory.getDefaultInstance(), new FileReader(CLIENT_SECRET_FILE));
            } catch (IOException e) {
                throw e;
            }
        }
*/
        GoogleTokenResponse tokenResponse=null;

        {
            try {
                tokenResponse = new GoogleAuthorizationCodeTokenRequest(
                        new NetHttpTransport(),
                        JacksonFactory.getDefaultInstance(),
                        "https://www.googleapis.com/oauth2/v4/token",
                        "895714867508-2t0rmc94tp81bfob19lre1lot6djoiuu.apps.googleusercontent.com",
                        "FGLsX3PBtIHEypj88z7UkI6R",
                        authCode,
                        redirectUrl)  // Specify the same redirect URI that you use with your web
                        //                    // app. If you don't have a web version of your app, you can
                        // specify an empty string.
                        .execute();
            } catch (IOException e) {
                throw e;

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
                throw e;
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
        AppUser user=new AppUser(userId,email,"123456",name,accessToken,refreshToken);
        return user;
    }

}
