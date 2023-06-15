package toyboard.yoon.comment.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import toyboard.yoon.entity.Article;
import toyboard.yoon.entity.member.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comment_id")
    private Long id;

    private Content content;

    @CreatedDate
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Article article;

    protected Comment() {
    }

    @Builder
    private Comment(final String content, final Member member, final Article article) {
        this.content = Content.from(content);
        this.member = member;
        this.article = article;
    }

    public Comment(final Long id, final String content, final Member member, final Article article) {
        this.id = id;
        this.content = Content.from(content);
        this.member = member;
        this.article = article;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
