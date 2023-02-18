package error;
public class FileNotCreateException extends Exception {
    public FileNotCreateException() {
    }

    public FileNotCreateException(String msg) {
        super(msg);
    }
}