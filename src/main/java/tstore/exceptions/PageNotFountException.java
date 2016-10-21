package tstore.exceptions;

/**
 * Created by mipan on 21.10.2016.
 */
public class PageNotFountException extends RuntimeException {
    public PageNotFountException() {
        super();
    }

    public PageNotFountException(String message) {
        super(message);
    }
}
