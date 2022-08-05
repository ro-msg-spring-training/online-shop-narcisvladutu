package ro.msg.learning.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDtoSave {
    private Integer customerId;
    private LocalDateTime createdAt = LocalDateTime.now();
    private String addressCountry;
    private String addressCity;
    private String addressCounty;
    private String addressStreetAddress;
    private List<OrderDetailDtoSave> orderDetailDtoSaveList;
}
