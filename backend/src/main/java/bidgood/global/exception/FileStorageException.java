package bidgood.global.exception;

public class FileStorageException extends BidGoodException{
    private final static String MESSAGE = "_파일 업로드에 실패했습니다.";

    public FileStorageException(String fileName) {
        super(fileName + MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
