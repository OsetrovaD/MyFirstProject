package project.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import project.entity.enumonly.AgeLimit;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AgeLimitService {

    private static final AgeLimitService INSTANCE = new AgeLimitService();

    public List<AgeLimit> getAll() {
        return Arrays.asList(AgeLimit.values());
    }

    public static AgeLimitService getInstance() {
        return INSTANCE;
    }
}
