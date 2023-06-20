package toyboard.yoon.like.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import toyboard.yoon.entity.member.Member;
import toyboard.yoon.like.dto.ArticleLikeResponse;
import toyboard.yoon.like.service.ArticleLikeService;
import toyboard.yoon.security.JwtAuth;

@RestController
@RequiredArgsConstructor
public class ArticleLikeController {

    private final ArticleLikeService articleLikeService;

    @PostMapping("/articles/{articleId}/like")
    public ResponseEntity<ArticleLikeResponse> likePost(@JwtAuth Member member,
                                                        @PathVariable Long articleId) {
        ArticleLikeResponse articleLikeResponse = articleLikeService.flipPostLike(member, articleId);
        return ResponseEntity.ok(articleLikeResponse);
    }
}
