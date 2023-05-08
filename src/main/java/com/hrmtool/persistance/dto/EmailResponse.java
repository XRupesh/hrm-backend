package com.hrmtool.persistance.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailResponse {

    private String messageId;
    private int statusCode;
    private String message;
}
