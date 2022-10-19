package nsw.revenue.coding.challenge.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel(value = "Insurer", description = "This Model keep Insurer details")
@Entity
@Table(name = "Insurers")
public class Insurer {

    @ApiModelProperty(
        value = "name",
        example = "Allianz")
    @NotEmpty(message = "name can't be empty !")
    @Column(name = "name", unique = true)
    @Id
    private String name;

    @ApiModelProperty(
        value = "code",
        example = "32")
    @NotEmpty(message = "code can't be empty !")
    @Pattern(regexp = "\\d+", message = "Invalid code")
    @Column(name = "code")
    private String code;

    @OneToOne(mappedBy = "insurer")
    @JsonIgnore
    private VehicleRegistration vehicleRegistration;
}
