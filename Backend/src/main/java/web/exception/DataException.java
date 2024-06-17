package web.exception;

public class DataException extends NullPointerException {
    public DataException(String reason) {
        super(reason);
    }
}
