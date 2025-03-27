package bidgood.global.exception;

public class FileUploadException extends BidGoodException{
    private static final String MESSAGE = "일부 파일 업로드가 실패했습니다.\n경매품 수정을 통해서 추가 업로드 하세요";

    public FileUploadException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 500;
    }
}
