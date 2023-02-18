package error;
public class FolderNotCreateException extends Exception {
    public FolderNotCreateException() {
    }

    public FolderNotCreateException(String msg) {
        super(msg);
    }
}