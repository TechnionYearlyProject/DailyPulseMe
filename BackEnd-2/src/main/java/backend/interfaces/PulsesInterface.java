package backend.interfaces;

import backend.DailyPulseApp;
import backend.entity.AppUser;
import backend.entity.Event;
import backend.entity.Pulse;
import backend.entity.RefreshTokenExpiredException;
import backend.googleFitApi.GoogleCallParser;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class PulsesInterface {
    static final String FITBIT_BAND = "FitBit";
    static final String GOOGLE_FIT_BAND = "GoogleFit";

    public static List<Pulse> getPulsesByType(AppUser user, String startTime, String endTime, String MinInMs, String bandType) {
        List<Pulse> pulses = null;
        switch (bandType){
            case FITBIT_BAND:
                pulses = getFitBitPulses(user, startTime, endTime, MinInMs);
                break;
            case GOOGLE_FIT_BAND:
                pulses = getGoogleFitPulses(user, startTime, endTime, MinInMs);
                break;
            default:
                break;
        }
        return pulses;
    }

    private static List<Pulse> getGoogleFitPulses(AppUser user, String startTime, String endTime, String minInMs) {
        List<Pulse> pulses = null;
        try {
            pulses = GoogleCallParser.getPulses(user, startTime, endTime, minInMs);
        }
        catch (RefreshTokenExpiredException e) {
            DailyPulseApp.LOGGER.info("error from backend: " + e.toString());
        }
        return pulses;
    }


    private static List<Pulse> getFitBitPulses(AppUser user, String startTime, String endTime, String minInMs) {
        String js;
        String accessToken=user.getAccessToken();
        //System.out.println(accessToken);
        //TODO: CHANGE TIME AND DATE IN THE REQUEST
        HttpGet get = new HttpGet("https://api.fitbit.com/1/user/-/activities/heart/date/today/1d.json");
        get.addHeader("Authorization" , "Bearer "+accessToken);
        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = null;
        try {
            response = client.execute(get);

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatusLine().getStatusCode());
            }

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            (response.getEntity().getContent())
                    )
            );
            StringBuilder content = new StringBuilder();
            String line;
            while (null != (line = br.readLine())) {
                content.append(line);
            }
            //OLD WAY:
            //Gson gson = new Gson();
            //heartrate name = gson.fromJson(content.toString(),heartrate.class);
            //TODO: use regex to extract the pulses from the response.

        }
        catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            get.releaseConnection();
        }
        return null;
    }

}
