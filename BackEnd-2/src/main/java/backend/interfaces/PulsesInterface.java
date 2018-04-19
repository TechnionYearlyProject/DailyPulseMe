package backend.interfaces;

import backend.DailyPulseApp;
import backend.entity.AppUser;
import backend.entity.Pulse;
import backend.entity.RefreshTokenExpiredException;
import backend.fitBitApi.FitBitCallParser;
import backend.googleFitApi.GoogleCallParser;
import backend.helperClasses.BandType;

import java.util.List;

public class PulsesInterface {
    static final String PERIOD = "1m";

    public static List<Pulse> getPulsesByType(AppUser user, String startTime, String endTime, String MinInMs)
    throws  RefreshTokenExpiredException{

        BandType bandType = user.getActiveBandType();
        List<Pulse> pulses = null;
        switch (bandType){
            case FITBIT_BAND:
                pulses = FitBitCallParser.getFitBitPulses(user, startTime, endTime, MinInMs);
                break;
            case GOOGLEFIT_BAND:
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
