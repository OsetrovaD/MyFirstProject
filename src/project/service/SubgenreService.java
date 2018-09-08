package project.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import project.dao.SubgenreDao;
import project.entity.Subgenre;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SubgenreService {

    private static final SubgenreService INSTANCE = new SubgenreService();

    public List<Subgenre> getAll() {
        return SubgenreDao.getInstance().getAll();
    }

    public static SubgenreService getInstance() {
        return INSTANCE;
    }
}
