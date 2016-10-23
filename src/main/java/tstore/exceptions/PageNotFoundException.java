package tstore.exceptions;

/**
 * Created by mipan on 21.10.2016.
 */
public class PageNotFoundException extends RuntimeException {
    public PageNotFoundException() {
        super();
    }

    public PageNotFoundException(String message) {
        super(message);
    }
}
