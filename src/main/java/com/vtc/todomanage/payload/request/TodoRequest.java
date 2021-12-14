package com.vtc.todomanage.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class TodoRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String content;
}
