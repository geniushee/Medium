package com.ll.medium.domain.aticle.article.Data;

import com.ll.medium.domain.aticle.article.DTO.ArticleDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ArticleForm {
    @NotEmpty(message = "제목은 필수항목입니다.")
    private final String title;
    @NotEmpty(message = "내용은 필수항목입니다.")
    private final String body;
    @NotNull
    private final boolean published;
    private boolean isPaid;

    public ArticleDto toDto() {
        return ArticleDto.builder()
                .title(this.title)
                .body(this.body)
                .published(this.published)
                .isPaid(this.isPaid)
                .build();
    }
}
