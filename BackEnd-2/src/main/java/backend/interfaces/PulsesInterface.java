package backend.interfaces;

import backend.DailyPulseApp;
import backend.entity.AppUser;
import backend.entity.Event;
import backend.entity.Pulse;
import backend.entity.RefreshTokenExpiredException;
import backend.fitBitApi.fitBitCallParser;
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
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PulsesInterface {
    static final String FITBIT_BAND = "FitBit";
    static final String GOOGLE_FIT_BAND = "GoogleFit";
    static final String PERIOD = "1m";

    public static List<Pulse> getPulsesByType(AppUser user, String startTime, String endTime, String MinInMs, String bandType) {
        List<Pulse> pulses = null;
        switch (bandType){
            case FITBIT_BAND:
                pulses = fitBitCallParser.getFitBitPulses(user, startTime, endTime, MinInMs);
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



}
