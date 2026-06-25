package exception;

public class DuplicateApplication extends Exception{
    public DuplicateApplication(String message) {
        super(message);
    }
}
