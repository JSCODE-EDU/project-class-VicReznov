package toyboard.yoon.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import toyboard.yoon.comment.dto.CommentSaveRequest;
import toyboard.yoon.comment.dto.CommentSaveResponse;
import toyboard.yoon.comment.service.CommentService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/articles/{articleId}/comments")
    public ResponseEntity<CommentSaveResponse> addComment(@PathVariable final Long postId,
                                                          @Valid @RequestBody CommentSaveRequest commentSaveRequest) {
        CommentSaveResponse commentSaveResponse = commentService.addComment(postId, commentSaveRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(commentSaveResponse);
    }
}
