package project.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import project.dao.CommentDao;
import project.entity.Comment;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentService {

    private static final CommentService INSTANCE = new CommentService();

    public List<Comment> getByUserOrGameId(Long userId) {
        return CommentDao.getInstance().getByUserId(userId);
    }

    public static CommentService getInstance() {
        return INSTANCE;
    }
}
