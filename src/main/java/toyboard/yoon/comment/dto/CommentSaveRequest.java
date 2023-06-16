package toyboard.yoon.comment.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class CommentSaveRequest {

    @NotBlank(message = "댓글을 반드시 입력해야 합니다.")
    private String content;

    private CommentSaveRequest() {
    }

    public CommentSaveRequest(final String content) {
        this.content = content;
    }
}
