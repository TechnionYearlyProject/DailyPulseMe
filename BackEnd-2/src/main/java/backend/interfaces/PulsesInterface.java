package backend.interfaces;

import backend.DailyPulseApp;
import backend.entity.AppUser;
import backend.entity.Pulse;
import backend.entity.RefreshTokenExpiredException;
import backend.fitBitApi.FitBitCallParser;
import backend.googleFitApi.GoogleCallParser;

import java.util.List;

public class PulsesInterface {
    static final String FITBIT_BAND = "FitBit";
    static final String GOOGLE_FIT_BAND = "GoogleFit";
    static final String PERIOD = "1m";

    public static List<Pulse> getPulsesByType(AppUser user, String startTime, String endTime, String MinInMs, String bandType) {
        List<Pulse> pulses = null;
        switch (bandType){
            case FITBIT_BAND:
                pulses = FitBitCallParser.getFitBitPulses(user, startTime, endTime, MinInMs);
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
            pulses = GoogleCallParser.getGoogleFitPulses(user, startTime, endTime, minInMs);
        }
        catch (RefreshTokenExpiredException e) {
            DailyPulseApp.LOGGER.info("error from backend: " + e.toString());
        }
        return pulses;
    }



}
