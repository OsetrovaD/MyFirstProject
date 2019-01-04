package project.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import project.entity.enumonly.PaymentForm;

import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentFormService {

    private static final PaymentFormService INSTANCE = new PaymentFormService();

    public List<PaymentForm> getAll() {
        return Arrays.asList(PaymentForm.values());
    }

    public static PaymentFormService getInstance() {
        return INSTANCE;
    }
}
