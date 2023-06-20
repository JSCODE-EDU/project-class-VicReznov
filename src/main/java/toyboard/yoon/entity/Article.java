package toyboard.yoon.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import toyboard.yoon.entity.member.Member;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;

@Entity
@Table(name = "article", schema="toy_board", uniqueConstraints = {@UniqueConstraint(columnNames = "article_id")})
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "article_id", unique = true, nullable = false)
    private Long articleId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Article() {

    }


    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isWriter(Long updaterId) {
        return this.member.getId()
                .equals(updaterId);
    }
}
