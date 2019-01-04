package project.service;

import project.entity.enumonly.DeliveryMethod;

import java.util.Arrays;
import java.util.List;

public class DeliveryMethodService {

    private static final DeliveryMethodService INSTANCE = new DeliveryMethodService();

    public List<DeliveryMethod> getAll() {
        return Arrays.asList(DeliveryMethod.values());
    }

    public static DeliveryMethodService getInstance() {
        return INSTANCE;
    }
}
