package project.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import project.dao.GenreDao;
import project.entity.Genre;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GenreService {

    private static final GenreService INSTANCE = new GenreService();

    public List<Genre> getAll() {
        return GenreDao.getInstance().getAll();
    }

    public static GenreService getInstance() {
        return INSTANCE;
    }
}
