package nsw.revenue.coding.challenge.model;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class Credential {

    private String username;

    @ApiModelProperty(
        value = "Password must be Minimum 4 characters",
        example = "Leila123")
    @NotEmpty(message = "password can't be empty !")
    @Length(min = 4, message = "Password must be Minimum 4 characters")
    private String password;
}
