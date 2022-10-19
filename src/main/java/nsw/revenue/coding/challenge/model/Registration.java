package nsw.revenue.coding.challenge.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.OffsetDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel(value = "Registration", description = "This Model keep Registration details")
@Entity
@Table(name = "registrations")
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ApiModelProperty(
        value = "Expired flag",
        example = "false")
    @Column(name = "expired")
    private Boolean expired;

    @ApiModelProperty(
        value = "Expire date",
        example = "2021-03-13T17:30:07.999725+11:00")
    @Column(name = "expiryDate", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime expiryDate;

    @OneToOne(mappedBy = "registration")
    @JsonIgnore
    private VehicleRegistration vehicleRegistration;
}



