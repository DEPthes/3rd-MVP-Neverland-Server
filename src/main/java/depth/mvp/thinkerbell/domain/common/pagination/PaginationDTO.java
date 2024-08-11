package depth.mvp.thinkerbell.domain.common.pagination;

import lombok.Data;
import java.util.List;

@Data
public class PaginationDTO<T> {  // 여기서 <T>를 선언
    private List<T> items;
    private int page;
    private int size;
    private long totalItems;

    public PaginationDTO(List<T> items, int page, int size, long totalItems) {
        this.items = items;
        this.page = page;
        this.size = size;
        this.totalItems = totalItems;
    }
}