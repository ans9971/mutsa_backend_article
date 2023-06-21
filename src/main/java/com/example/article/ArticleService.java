package com.example.article;

import com.example.article.dto.ArticleDto;
import com.example.article.entity.ArticleEntity;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository repository;

    public ArticleDto createArticle(ArticleDto dto) {
//        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);

        ArticleEntity articleDto = new ArticleEntity();
//        articleDto.setId(4);
        articleDto.setTitle(dto.getTitle());
        articleDto.setContent(dto.getContent());
        articleDto.setWriter(dto.getWriter());
//        articleDto= this.repository.save(articleDto);
        return ArticleDto.fromEntity(repository.save(articleDto));
    }


    public ArticleDto readArticle(Long id) {
//        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
//        ArticleEntity article = new ArticleEntity();
        Optional<ArticleEntity> article = repository.findById(id);//findbyid 하면 optional객체가 들어온다
        //optional안에 Article이 들어있으면
        if (article.isPresent()) {
            return ArticleDto.fromEntity(article.get());//optional 내부에있는걸 받아올때는 객체.get() 해줘야한다..
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

//        if (article.isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//        }
//         ArticleDto.fromEntity(article.get());
        //DTO로 변환후 반환
        //아니면 404
    }

    public List<ArticleDto> readArticleAll() {
//        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
        List<ArticleDto> articleDtoList = new ArrayList<>();
        for (ArticleEntity entity : repository.findAll()) {
            articleDtoList.add(ArticleDto.fromEntity(entity));
        }
        return articleDtoList;
    }


    public ArticleDto updateArticle(Long id, ArticleDto dto) {
//        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
        Optional<ArticleEntity> article = repository.findById(id);
        if (article.isPresent()) {
            article.get().setWriter(dto.getWriter());
            article.get().setTitle(dto.getTitle());
            article.get().setContent(dto.getContent());
            repository.save(article.get());
        }else throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return ArticleDto.fromEntity(article.get());
//        return ArticleDto.fromEntity(article.get());
    }



    public void deleteArticle(Long id) {
//        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
        Optional<ArticleEntity> article = repository.findById(id);
        if (article.isPresent()) {
            repository.delete(article.get());
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND);

    }
}
