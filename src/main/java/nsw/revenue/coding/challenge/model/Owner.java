package nsw.revenue.coding.challenge.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModelProperty.AccessMode;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Owner", description = "This Model keep Owner details")
@Entity
@Table(name = "owners")
public class Owner {

    @NonNull
    @ApiModelProperty(
        value = "username",
        accessMode = AccessMode.READ_ONLY,
        example = "Leila")
    @NotEmpty(message = "username can't be empty !")
    @Column(name = "username", unique = true)
    @Id
    private String username;

    @NonNull
    @ApiModelProperty(
        value = "Password must be Minimum 4 characters",
        example = "Leila123")
    @NotEmpty(message = "password can't be empty !")
    @Length(min = 4, message = "Password must be Minimum 4 characters")
    @Column(name = "password")
    @JsonIgnore
    private String password;


    @OneToMany(mappedBy = "owner")
    private Set<VehicleRegistration> vehicleRegistrations;

}
