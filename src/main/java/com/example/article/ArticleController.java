package com.example.article;

import com.example.article.dto.ArticleDto;
import jakarta.persistence.GeneratedValue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.management.DescriptorKey;
import java.util.List;


@Slf4j
@RestController //@Responsebody 생략 가능
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService service;

    // POST /articles
    @PostMapping("/articles")
    //RESTful한 api는 행동의 결과로 반영된 자원의 상태를 반환함이 옳다
    public ArticleDto create(@RequestBody ArticleDto dto) {//requestbody가붙으면 역직렬화를 해준다. 역직렬화 = json -> dto
        return service.createArticle(dto);

    }

    // GET /articles
    @GetMapping("/articles")
    public List<ArticleDto> readAll() {
        return service.readArticleAll();
    }

    // GET /articles/{id}
    @GetMapping("/articles/{id}")
    public ArticleDto read(@PathVariable("id") Long id) {
        return service.readArticle(id);
    }

    // PUT /articles/{id}
    @PutMapping("/articles/{id}")
    public ArticleDto update(@PathVariable("id") Long id, @RequestBody ArticleDto dto) {
        return service.updateArticle(id, dto);
    }

    // DELETE /articles/{id}
    @DeleteMapping("/articles/{id}")
    public void delete(@PathVariable("id") Long id) {
        service.deleteArticle(id);
    }
}
