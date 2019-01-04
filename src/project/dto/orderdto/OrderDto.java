package project.dto.orderdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.entity.enumonly.DeliveryMethod;
import project.entity.enumonly.PaymentForm;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {

    private Long userId;
    private DeliveryMethod deliveryMethod;
    private PaymentForm paymentForm;
}
