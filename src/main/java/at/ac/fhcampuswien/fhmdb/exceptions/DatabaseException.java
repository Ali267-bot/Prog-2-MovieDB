package at.ac.fhcampuswien.fhmdb.exceptions;

public class DatabaseException extends Exception {
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
    public DatabaseException(String message) {
        super(message);
    }

    public static class ConnectionException extends DatabaseException {
        public ConnectionException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class OperationException extends DatabaseException {

        public OperationException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
