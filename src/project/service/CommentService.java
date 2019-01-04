package project.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import project.dao.CommentDao;
import project.dto.commentdto.GameCommentDto;
import project.dto.commentdto.NewCommentDto;
import project.dto.commentdto.UserCommentDto;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentService {

    private static final CommentService INSTANCE = new CommentService();

    public List<UserCommentDto> getByUserId(Long gameId) {
        return CommentDao.getInstance().getByUserId(gameId);
    }

    public List<GameCommentDto> getByGameId(Long id) {
        return CommentDao.getInstance().getByGameId(id);
    }

    public Long add(NewCommentDto comment) {
        return CommentDao.getInstance().save(comment);
    }

    public static CommentService getInstance() {
        return INSTANCE;
    }
}
