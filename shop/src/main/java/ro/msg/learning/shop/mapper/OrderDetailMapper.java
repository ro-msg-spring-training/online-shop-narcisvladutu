package ro.msg.learning.shop.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.dto.OrderDetailDto;
import ro.msg.learning.shop.dto.OrderDetailDtoSave;
import ro.msg.learning.shop.model.OrderDetail;
import ro.msg.learning.shop.model.Product;

@Component
@RequiredArgsConstructor
public class OrderDetailMapper {
    public OrderDetailDto toDto(OrderDetail orderDetail) {
        return new OrderDetailDto(orderDetail.getOrder().getId(), orderDetail.getProduct().getName(), orderDetail.getQuantity());
    }

    public OrderDetail toOrderDetail(OrderDetailDtoSave orderDetailDtoSave) {
        Product product = new Product();
        product.setId(orderDetailDtoSave.getProductId());
        return new OrderDetail(null, product, null, orderDetailDtoSave.getQuantity());
    }
}
