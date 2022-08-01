package ro.msg.learning.shop.model;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "LOCATION")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location extends BaseEntity<Integer> {
    @Column(name = "name", nullable = false)
    private String name;

    @Embedded
    @JsonUnwrapped
    private Address address;
}
