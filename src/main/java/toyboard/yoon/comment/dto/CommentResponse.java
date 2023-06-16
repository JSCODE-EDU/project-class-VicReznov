package toyboard.yoon.comment.dto;

import lombok.Builder;
import lombok.Getter;
import toyboard.yoon.comment.domain.Comment;

import java.time.LocalDateTime;

@Getter
public class CommentResponse {

    private final String content;
    private final LocalDateTime createdAt;
    private final String email;

    @Builder
    private CommentResponse(final String content, final LocalDateTime createdAt, final String email) {
        this.content = content;
        this.createdAt = createdAt;
        this.email = email;
    }

    public static CommentResponse from(final Comment comment) {
        return CommentResponse.builder()
                .content(String.valueOf(comment.getContent()))
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
