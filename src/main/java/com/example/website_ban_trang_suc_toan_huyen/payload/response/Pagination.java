package com.example.website_ban_trang_suc_toan_huyen.payload.response;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Pagination {
    private int page;

    @JsonProperty("page_size")
    private int pageSize;

    @JsonProperty("total_page")
    private int totalPage;

    @JsonProperty("total_item")
    private long totalItem;
}
