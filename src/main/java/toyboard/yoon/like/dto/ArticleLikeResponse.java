package toyboard.yoon.like.dto;

import lombok.Getter;

@Getter
public class ArticleLikeResponse {

    private boolean like;

    private ArticleLikeResponse() {
    }

    public ArticleLikeResponse(final boolean like) {
        this.like = like;
    }
}
