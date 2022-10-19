package nsw.revenue.coding.challenge.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModelProperty.AccessMode;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@ApiModel(value = "OwnerVehicleRegistration", description = "This Model keep Registered vehicles for a user ")
@Entity
@Table(name = "vehicle_registration")
public class VehicleRegistration {

    @NonNull
    @ApiModelProperty(
        value = "Plate number",
        accessMode = AccessMode.READ_ONLY,
        example = "EBF28ECsC")
    @NotEmpty(message = "Plate number can't be empty !")
    @Column(name = "plateNumber", unique = true)
    @Id
    private String plateNumber;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "registration", referencedColumnName = "id")
    private Registration registration;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vehicle", referencedColumnName = "id")
    private Vehicle vehicle;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "insurer", referencedColumnName = "name")
    private Insurer insurer;


    @ManyToOne
    @JoinColumn(name = "owner_username", nullable = false)
    @JsonIgnore
    private Owner owner;
}
