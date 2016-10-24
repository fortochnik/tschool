package tstore.utils;

import tstore.model.enums.Role;

/**
 * Created by mipan on 05.10.2016.
 *
 * Attributes that use in servlets
 *
 */
public class SessionAttributes {
    /**
     * LOGIN true if user ligin in system
     * ROLE value od {@link Role}
     * USERID user id if LOGIN true
     * BASKET quantity of products in the basket
     */
    public final static String LOGIN = "LOGIN";
    public final static String ROLE = "ROLE";
    public static final String USERID = "USERID";
    public static final String BASKET = "BASKET";

    private SessionAttributes() {
    }
}
