package com.example.rentalservice.model.search;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PagingResponse<T> {
    //Danh sách dữ liệu
    private List<T> data;

    //Tổng số dữ liệu
    private long total;

    public PagingResponse(Page<T> page) {
        this.total = page.getTotalElements();
        this.data = page.getContent();
    }
}
