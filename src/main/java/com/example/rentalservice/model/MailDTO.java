package com.example.rentalservice.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MailDTO {
    private List<String> mailTo = new ArrayList<>();
    private List<String> mailCc = new ArrayList<>();
    private String subject;
    private String content;
}
