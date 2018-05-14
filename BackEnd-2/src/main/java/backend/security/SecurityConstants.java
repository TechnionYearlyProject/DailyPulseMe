package backend.security;

public class SecurityConstants {
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users/sign-up";

    //TODO: Remove from here and find a safe way
    public static final String GET_SUBSCRIBTIONS_URL = "/users/getSubscribedUsers";
    public static final String SEND_EMAILS_URL = "/users/sendEmails";
}