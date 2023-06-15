package toyboard.yoon.comment.dto;

import lombok.Getter;

@Getter
public class CommentSaveResponse {

    private Long savedId;

    private CommentSaveResponse() {
    }

    public CommentSaveResponse(final Long savedId) {
        this.savedId = savedId;
    }
}
