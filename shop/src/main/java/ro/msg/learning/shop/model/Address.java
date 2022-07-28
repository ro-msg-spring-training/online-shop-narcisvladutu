package ro.msg.learning.shop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@AttributeOverride(name = "country", column = @Column(name = "country"))
@AttributeOverride(name = "city", column = @Column(name = "city"))
@AttributeOverride(name = "county", column = @Column(name = "county"))
@AttributeOverride(name = "streetAddress", column = @Column(name = "street_address"))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String country;
    private String city;
    private String county;
    private String streetAddress;
}