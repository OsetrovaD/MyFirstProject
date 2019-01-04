package project.dto.orderdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.dto.userdto.UserLoginIdDto;
import project.entity.enumonly.Condition;
import project.entity.enumonly.DeliveryMethod;
import project.entity.enumonly.PaymentForm;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserOrderForAdminDto {

    private Long id;
    private DeliveryMethod deliveryMethod;
    private Condition condition;
    private PaymentForm paymentForm;
    private LocalDate date;
    private LocalDate deliveryDate;
    private UserLoginIdDto user;
}
