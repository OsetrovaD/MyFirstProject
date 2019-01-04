package project.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import project.entity.enumonly.Condition;

import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConditionService {

    private static final ConditionService INSTANCE = new ConditionService();

    public List<Condition> getAll() {
        return Arrays.asList(Condition.values());
    }

    public static ConditionService getInstance() {
        return INSTANCE;
    }
}
