package com._oormthonuniv.Klay.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PageResponseDTO<T> {
    private int totalCount;
    private int pageNumber;
    private boolean isEnd;
    private List<T> data;

    public PageResponseDTO(Page<T> page) {
        this.totalCount = (int) page.getTotalElements();
        this.pageNumber = page.getNumber();
        this.isEnd = page.isLast();
        this.data = page.getContent();
    }
}
