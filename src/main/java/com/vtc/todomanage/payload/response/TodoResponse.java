package com.vtc.todomanage.payload.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TodoResponse {
    private String title;
    private String content;

    public TodoResponse(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
