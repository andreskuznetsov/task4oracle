package es.ahs.oracle_task.utils.exception;

/**
 * Created by akuznetsov on 09.09.2016.
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
