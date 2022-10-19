package nsw.revenue.coding.challenge.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel(value = "Vehicle", description = "This Model keep Vehicle details")
@Entity
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ApiModelProperty(
        value = "type",
        example = "Wagon")
    @NotEmpty(message = "type can't be empty !")
    private String type;

    @ApiModelProperty(
        value = "make",
        example = "BMW")
    @NotEmpty(message = "make can't be empty !")
    private String make;

    @ApiModelProperty(
        value = "model",
        example = "X4 M40i")
    @NotEmpty(message = "model can't be empty !")
    private String model;

    @ApiModelProperty(
        value = "colour",
        example = "Blue")
    @NotEmpty(message = "colour can't be empty !")
    private String colour;

    @ApiModelProperty(
        value = "vin",
        example = "12389347324")
    @NotEmpty(message = "vin can't be empty !")
    private String vin;

    @ApiModelProperty(
        value = "tareWeight",
        example = "1700")
    private Long tareWeight;


    @ApiModelProperty(
        value = "grossMass",
        example = "")
    private String grossMass;

    @OneToOne(mappedBy = "vehicle")
    @JsonIgnore
    private VehicleRegistration vehicleRegistration;
}
