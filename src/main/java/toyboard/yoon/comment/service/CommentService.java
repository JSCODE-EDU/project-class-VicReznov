package toyboard.yoon.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyboard.yoon.comment.domain.Comment;
import toyboard.yoon.comment.dto.CommentSaveRequest;
import toyboard.yoon.comment.dto.CommentSaveResponse;
import toyboard.yoon.comment.repository.CommentRepository;
import toyboard.yoon.entity.Article;
import toyboard.yoon.entity.member.Member;
import toyboard.yoon.repository.ArticleRepository;
import toyboard.yoon.repository.MemberRepository;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;


    @Transactional
    public CommentSaveResponse addComment(final Long articleId,
                                          final CommentSaveRequest commentSaveRequest) {

        Article article = findArticle(articleId);
        Comment comment = Comment.builder()
                .content(commentSaveRequest.getContent())
                .article(article)
                .build();
        Comment savedComment = commentRepository.save(comment);
        return new CommentSaveResponse(savedComment.getId());
    }

    private Article findArticle(final Long postId) {
        return articleRepository.findById(postId)
                .orElseThrow(EntityNotFoundException::new);
    }

    private Member findMember(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(EntityNotFoundException::new);
    }
}
