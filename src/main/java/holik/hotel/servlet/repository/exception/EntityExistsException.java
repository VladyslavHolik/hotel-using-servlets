package holik.hotel.servlet.repository.exception;

public class EntityExistsException extends Exception {
    public EntityExistsException(String message) {
        super(message);
    }
}
