package ro.msg.learning.shop.model;


import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "REVENUE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Revenue extends BaseEntity<Integer> {
    @ManyToOne
    @JsonUnwrapped
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "sum", nullable = false)
    private BigDecimal sum;
}
