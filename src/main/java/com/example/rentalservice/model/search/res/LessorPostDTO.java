package com.example.rentalservice.model.search.res;

import com.example.rentalservice.common.DateUtils;
import com.example.rentalservice.model.post.ILessorPost;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LessorPostDTO {
    private String id;
    private String title;
    private Long numberWatch;
    private String createTime;
    private String firstImage;
    private Long numImage;

    public LessorPostDTO (ILessorPost iLessorPost) {
        this.id = iLessorPost.getId();
        this.title = iLessorPost.getTitle();
        this.numberWatch = iLessorPost.getNumberWatch();
        this.createTime = DateUtils.toStr(iLessorPost.getCreateTime(), DateUtils.F_HHMMSSDDMMYYYY);
        this.firstImage = iLessorPost.getFirstImage();
        this.numImage = iLessorPost.getNumImage();
    }
}
