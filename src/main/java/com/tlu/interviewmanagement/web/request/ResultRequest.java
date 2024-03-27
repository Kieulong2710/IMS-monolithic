package com.tlu.interviewmanagement.web.request;

import com.tlu.interviewmanagement.enums.EStatus;
import lombok.Data;

@Data
public class ResultRequest {
    private Long id;
    private EStatus result;
    private String note;
}
