package com.sword.framework.common.model.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Schema(description = "分页结果")
@Data
public class PageResult<T> implements Serializable {

    /**
     * 查询数据列表
     */
    @Schema(description = "查询数据列表")
    protected List<T> records = Collections.emptyList();

    /**
     * 总数
     */
    @Schema(description = "总数")
    protected Long total = 0L;

    /**
     * 每页显示条数，默认 10
     */
    @Schema(description = "每页显示条数，默认 10")
    protected Long size = 10L;

    /**
     * 当前页
     */
    @Schema(description = "当前页")
    protected Long current = 1L;


    public PageResult() {
    }

    public PageResult(List<T> records, Long total) {
        this.records = records;
        this.total = total;
    }

    public PageResult(Long total) {
        this.records = new ArrayList<>();
        this.total = total;
    }

    public static <T> PageResult<T> empty() {
        return new PageResult<>(0L);
    }

    public static <T> PageResult<T> empty(Long total) {
        return new PageResult<>(total);
    }

}
