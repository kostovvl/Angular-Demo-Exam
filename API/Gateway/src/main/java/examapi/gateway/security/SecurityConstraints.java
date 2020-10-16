package examapi.gateway.security;

public class SecurityConstraints {

    public static final String SECRET = "SECRET_KEY";
    public static final long EXPIRATION_TIME = 900_000; // 15 mins
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/auth/register";
    public static final String LOG_IN_URL = "/auth/login";
    public static final String ALL_CATEGORIES_URL = "/categories/all";


}
