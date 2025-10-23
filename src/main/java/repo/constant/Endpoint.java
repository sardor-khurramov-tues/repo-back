package repo.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class Endpoint {

    public static final String AUTHENTICATE = "/authenticate";
    public static final String REFRESH_TOKEN = "/refresh-token";
    public static final String SIGN_OUT = "/sign-out";
    public static final String SWAGGER_UI = "/swagger-ui";
    public static final String API_DOCS = "/v3/api-docs";
    public static final String ACTUATOR = "/actuator";
    public static final String ID_PATH_VARIABLE = "/{id}";

    public static final String USER = "/user";
    public static final String ADMIN = "/admin";
    public static final String STAFF = "/staff";
    public static final String AUTHOR = "/author";
    public static final String REGISTER = "/register";
    public static final String PASSWORD = "/password";
    public static final String BLOCK = "/block";

    public static final String DEPARTMENT = "/department";
    public static final String DOCUMENT = "/document";
    public static final String CONTRIBUTOR = "/contributor";
    public static final String DISSERTATION = "/dissertation";
    public static final String CONFERENCE_PROCEEDINGS = "/conference-proceedings";
    public static final String CONFERENCE_PAPER = "/conference-paper";
    public static final String BOOK = "/book";
    public static final String BOOK_CHAPTER = "/book-chapter";
    public static final String REPORT = "/report";

    public static final String FIND_BY = "/find-by";

    public static final String PUBLIC = "/public";
}
