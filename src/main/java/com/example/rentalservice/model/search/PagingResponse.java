package com.example.rentalservice.model.search;

import java.util.ArrayList;
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
    private List<T> data = new ArrayList<>();

    //Tổng số dữ liệu
    private long totalData = 0;

    private long totalPage = 0;

    public PagingResponse(Page<T> page) {
        this.totalPage = page.getTotalPages();
        this.totalData = page.getTotalElements();
        this.data = page.getContent();
    }

//    public PagingResponse(List<T> data, long totalPage, long totalData) {
//        this.data = data;
//        this.totalPage = totalPage;
//        this.totalData = totalData;
//    }
}
