package bidgood.product.dto.req;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductSearch {
    private static final long MAX_SIZE = Long.MAX_VALUE;
    private String searchWord;

    @Builder.Default
    private Integer page = 1;

    @Builder.Default
    private Integer size = 10;

    /**
     * Calculates the pagination offset based on the current page and size.
     *
     * <p>The offset is computed as the product of (page - 1) and the capped size, where the
     * page is ensured to be at least 1 and the size does not exceed MAX_SIZE.</p>
     *
     * @return the computed offset for pagination
     */
    public long getOffset(){
        return (long)(Math.max(1,page)-1)*Math.min(size,MAX_SIZE);
    }
}
