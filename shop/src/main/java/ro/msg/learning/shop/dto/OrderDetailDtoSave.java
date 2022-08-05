package ro.msg.learning.shop.dto;

import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDtoSave {
    private Integer productId;
    @Size(min = 1)
    private Integer quantity;
}