package entity;

import lombok.Data;

import java.util.List;

/**
 * @Description 分页结果类
 * @Author shawn
 * @create 2019/2/11 0011
 */
@Data
public class PageResult<T> {
    private Long total;
    private List<T> rows;

    public PageResult() {
    }

    public PageResult(Long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

}
