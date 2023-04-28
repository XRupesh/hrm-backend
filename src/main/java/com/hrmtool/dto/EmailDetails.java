package com.hrmtool.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailDetails {

    private String fromEmail;
    private String toEmail;
    private String Subject;
    private String body;
    private String firstName;

}
