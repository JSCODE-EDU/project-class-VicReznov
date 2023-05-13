package toyboard.yoon.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class ArticleTest {

    @Test
    @DisplayName("제목 길이가 15자 이상인 경우 유효성 테스트")
    void verifyTitleLength() {
        // given
        Article article = new Article();
        String title = "123456789101112131415";
        // when

        // then
        assertThrows(IllegalArgumentException.class, () -> {
            article.setTitle(title);
        });
    }

    @Test
    @DisplayName("제목이 공백으로만 이루어진 경우 유효성 테스트")
    void verifyTitleBlank() {
        // given
        Article article = new Article();
        String title = "       ";
        // when

        // then
        assertThrows(IllegalArgumentException.class, () -> {
            article.setTitle(title);
        });
    }

    @Test
    @DisplayName("내용 길이가 0일 때 유효성 테스트")
    void verifyContentsLength() {
        // given
        Article article = new Article();
        String contents = "";
        // when

        // then
        assertThrows(IllegalArgumentException.class, () -> {
            article.setContents(contents);
        });

    }
}
