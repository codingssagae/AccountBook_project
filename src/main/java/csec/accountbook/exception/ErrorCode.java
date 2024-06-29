package csec.accountbook.exception;

public enum ErrorCode {
    EMPTY_FILE_EXCEPTION("The provided file is empty."),
    NO_FILE_EXTENTION("The provided file has no extension."),
    INVALID_FILE_EXTENTION("The provided file extension is not supported."),
    IO_EXCEPTION_ON_IMAGE_UPLOAD("An I/O error occurred during image upload."),
    PUT_OBJECT_EXCEPTION("An error occurred while putting the object to S3."),
    IO_EXCEPTION_ON_IMAGE_DELETE("An I/O error occurred during image deletion.");

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
