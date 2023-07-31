package com.sword.framework.common.model.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

@Schema(description="分页参数")
@Data
public class PageParam implements Serializable {

    protected static final long PAGE_NO = 1;
    protected static final long PAGE_SIZE = 10;

    @Schema(description = "页码，从 1 开始", required = true,example = "1")
    @NotNull(message = "{global.page.pageNotNull}")
    @Min(value = 1, message = "{global.page.pageMin}")
    protected Long current = PAGE_NO;

    @Schema(description = "每页条数，最大值为 100", required = true, example = "10")
    @NotNull(message = "{global.page.sizeNotNull}")
    @Min(value = 1, message = "{global.page.sizeMin}")
    @Max(value = 100, message = "{global.page.sizeMax}")
    protected Long size = PAGE_SIZE;

}
