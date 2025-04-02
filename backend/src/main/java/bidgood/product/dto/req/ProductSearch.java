package bidgood.product.dto.req;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductSearch {
    private static final long MAX_SIZE = Long.MAX_VALUE;

    @Builder.Default
    private String searchWord = "";

    @Builder.Default
    private Integer page = 1;

    @Builder.Default
    private Integer size = 10;

    public long getOffset(){
        return (long)(Math.max(1,page)-1)*Math.min(size,MAX_SIZE);
    }
}
