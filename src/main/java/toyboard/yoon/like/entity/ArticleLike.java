package toyboard.yoon.like.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toyboard.yoon.entity.Article;
import toyboard.yoon.entity.member.Member;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Likes")
public class ArticleLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Article article;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Builder
    private ArticleLike(Article article, Member member) {
        this.article = article;
        this.member = member;
    }

    public static ArticleLike createLike(Article article, Member member) {
        return ArticleLike.builder()
                .article(article)
                .member(member)
                .build();
    }
}
