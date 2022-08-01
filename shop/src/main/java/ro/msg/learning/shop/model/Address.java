package ro.msg.learning.shop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serial;
import java.io.Serializable;

@Embeddable
@AttributeOverride(name = "country", column = @Column(name = "country"))
@AttributeOverride(name = "city", column = @Column(name = "city"))
@AttributeOverride(name = "county", column = @Column(name = "county"))
@AttributeOverride(name = "streetAddress", column = @Column(name = "street_address"))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address implements Serializable {
    @Serial
    private static final long serialVersionUID = -2936687026040726549L;

    private String country;
    private String city;
    private String county;
    private String streetAddress;
}
