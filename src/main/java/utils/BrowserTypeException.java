package utils;

public class BrowserTypeException extends Exception implements IBrowserProcessor{
    private final String errorMessage;
    public BrowserTypeException(String message) {
        super(message);
        this.errorMessage = message;
    }

    @Override
    public void initBrowser() throws BrowserTypeException {
        throw new BrowserTypeException(errorMessage);
    }
}
