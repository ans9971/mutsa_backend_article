package com.example.article;


import com.example.article.dto.CommentDto;
import com.example.article.entity.ArticleEntity;
import com.example.article.entity.CommentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;


    public CommentDto createComment(Long articleId, CommentDto dto) {
        //articleId를 ID로 가진 ArticleEntity가 존재하는지
        Optional<ArticleEntity> articleEntity = articleRepository.findById(articleId);
        if (!articleRepository.existsById(articleId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }else{
            CommentEntity newComment = new CommentEntity();
            newComment.setWriter(dto.getWriter());
            newComment.setContent(dto.getContent());
            newComment.setArticleId(articleEntity.get().getId());//또는 그냥 articleId 매개변수 써도된다.
            newComment = commentRepository.save(newComment);
            return CommentDto.fromEntity(newComment);
        }

    }
}
