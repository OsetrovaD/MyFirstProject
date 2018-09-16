package project.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import project.dao.DeveloperCompanyDao;
import project.entity.DeveloperCompany;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DeveloperCompanyService {

    private static final DeveloperCompanyService INSTANCE = new DeveloperCompanyService();

    public List<DeveloperCompany> getAll() {
        return DeveloperCompanyDao.getInstance().getAll();
    }

    public static DeveloperCompanyService getInstance() {
        return INSTANCE;
    }
}
