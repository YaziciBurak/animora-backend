package com.animora.comment.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentRequest {

    private String content;
}
