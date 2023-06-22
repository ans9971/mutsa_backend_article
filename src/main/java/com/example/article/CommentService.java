package com.example.article;


import com.example.article.dto.CommentDto;
import com.example.article.entity.ArticleEntity;
import com.example.article.entity.CommentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
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

    //TODO 게시글 댓글 전체 조회
    //반환타입 이름 인자
    public List<CommentDto> readCommentAll(Long articleId) {
        List<CommentEntity> commentEntities = commentRepository.findAllByArticleId(articleId);
        List<CommentDto> commentDtoList = new ArrayList<>();
        for (CommentEntity entity :
                commentEntities) {
            commentDtoList.add(CommentDto.fromEntity(entity));

        }
        return commentDtoList;
    }

    // TODO 게시글 댓글 수정
    public CommentDto updateComment(
            Long articleId,
            Long CommentId,
            CommentDto commentDto
    ) {
        //요청한 댓글이 존재하는지
        Optional<CommentEntity> optionalComment = commentRepository.findById(CommentId);

        //존재하지않으면 예외 발생
        if (optionalComment.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        //아니면 로직 진행
        CommentEntity comment = optionalComment.get();

        //대상 댓글이 대상 게시글의 댓글이 맞는지
        if (!articleId.equals(comment.getArticleId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        comment.setContent(comment.getContent());
        comment.setWriter(commentDto.getWriter());
        return CommentDto.fromEntity(comment);
    }

    // TODO 게시글 댓글 삭제
    public void deleteComment(Long articleId, Long commentId) {
        Optional<CommentEntity> commentEntity = commentRepository.findById(commentId);
        if (!commentRepository.existsById(articleId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        CommentEntity comment= commentEntity.get();

        if (!articleId.equals(comment.getArticleId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        commentRepository.deleteById(commentId);
    }

}
