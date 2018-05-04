package backend.CallParser;

import backend.entity.AppUser;
import backend.entity.Pulse;
import backend.entity.RefreshTokenExpiredException;

import java.util.List;

public abstract class CallParser {
    public abstract List<Pulse> getPulses(AppUser user, String startTime, String endTime, String minInMs) throws RefreshTokenExpiredException;

    public abstract boolean verifyAndRefresh(AppUser user);

    public abstract String refreshToken(AppUser user);
}