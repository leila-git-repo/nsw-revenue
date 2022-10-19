package nsw.revenue.coding.challenge.rest;


import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.validation.Valid;
import nsw.revenue.coding.challenge.model.Credential;
import nsw.revenue.coding.challenge.model.Owner;
import nsw.revenue.coding.challenge.model.VehicleRegistration;
import nsw.revenue.coding.challenge.repository.OwnerRepository;
import nsw.revenue.coding.challenge.repository.RegistrationRepository;
import nsw.revenue.coding.challenge.repository.VehicleRegistrationRepository;
import nsw.revenue.coding.challenge.repository.VehicleRepository;
import nsw.revenue.coding.challenge.util.CredentialUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Tag(
    name = "User Controller",
    description = ""
)
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private VehicleRegistrationRepository vehicleRegistrationRepository;

    @ApiOperation(value = "create new User(Owner)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "The request was processed successfully."),
        @ApiResponse(responseCode = "500", description = "There was an error processing this request."),
        @ApiResponse(responseCode = "400", description = "Invalid password format"),
        @ApiResponse(responseCode = "409", description = "username is duplicate")
    })
    @PostMapping(value = {"/"},
        consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity addOwner(@Valid @RequestBody Credential credential) {
        List<Owner> list = ownerRepository.findByUsername(credential.getUsername());
        if (!list.isEmpty() && list.size() > 0) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        Owner owner = new Owner(credential.getUsername(), passwordEncoder.encode(credential.getPassword()),
            Collections.emptySet());

        ownerRepository.save(owner);
        return ResponseEntity.ok().build();
    }


    @ApiOperation(value = "Register a vehicle for user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "The request was processed successfully."),
        @ApiResponse(responseCode = "500", description = "There was an error processing this request.")
    })
    @PostMapping(value = {"/vehicles/register"},
        consumes = {MediaType.APPLICATION_JSON_VALUE})
    @SecurityRequirement(name = "securityScheme")
    public ResponseEntity doRegistration(@Valid @RequestBody VehicleRegistration vehicleRegistration) {
        vehicleRegistration.setRegistration(registrationRepository.save(vehicleRegistration.getRegistration()));
        vehicleRegistration.setVehicle(vehicleRepository.save(vehicleRegistration.getVehicle()));
        Owner owner = ownerRepository.findById(CredentialUtil.getUsername()).get();
        vehicleRegistration.setOwner(owner);
        vehicleRegistrationRepository.save(vehicleRegistration);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Return registered owner vehicles ")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "The request was processed successfully."),
        @ApiResponse(responseCode = "500", description = "There was an error processing this request.")
    })
    @GetMapping(value = {"/vehicles"},
        produces = {MediaType.APPLICATION_JSON_VALUE})
    @SecurityRequirement(name = "securityScheme")
    public ResponseEntity<Set<VehicleRegistration>> getVehicles() {
        Owner owner = ownerRepository.findById(CredentialUtil.getUsername()).get();
        return ResponseEntity.ok().body(owner.getVehicleRegistrations());
    }

}
