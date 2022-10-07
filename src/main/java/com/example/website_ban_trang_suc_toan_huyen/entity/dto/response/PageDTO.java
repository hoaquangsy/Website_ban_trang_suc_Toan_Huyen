package com.example.website_ban_trang_suc_toan_huyen.entity.dto.response;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Data
public class PageDTO<T> implements Serializable {
    private PageableDTO page = new PageableDTO();

    private List<T> data;

    public PageDTO() {
    }

    public PageDTO(List<T> data, int pageIndex, int pageSize, long total) {
        this.data = data;
        page.setPageIndex(pageIndex);
        page.setPageSize(pageSize);
        page.setTotal(total);
    }

    public <U> PageDTO(Page<U> pageInput, Function<List<U>, List<T>> mapper) {
        Pageable pageable = pageInput.getPageable();
        page.setPageIndex(pageable.getPageNumber());
        page.setPageSize(pageable.getPageSize());
        page.setTotal(pageInput.getTotalElements());
        List<T> content = mapper.apply(pageInput.getContent());
        if (content != null) {
            this.data = content;
        }
    }

    public static <T> PageDTO<T> of(List<T> data, int pageIndex, int pageSize, long total) {
        return new PageDTO<>(data, pageIndex, pageSize, total);
    }

    public static <T> PageDTO<T> empty() {
        return new PageDTO<>(new ArrayList<>(), 1, 30, 0);
    }

    public static <T> PageDTO<T> empty(Pageable pageable) {
        return new PageDTO<>(new ArrayList<>(), pageable.getPageNumber(), pageable.getPageSize(), 0);
    }

    @Data
    public static class PageableDTO implements Serializable {
        private int pageIndex = 0;
        private int pageSize = 0;
        private long total = 0;
    }
}

