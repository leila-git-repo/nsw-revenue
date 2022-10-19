package nsw.revenue.coding.challenge.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;

import java.net.http.HttpResponse;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;
import nsw.revenue.coding.challenge.model.Insurer;
import nsw.revenue.coding.challenge.model.Registration;
import nsw.revenue.coding.challenge.model.Vehicle;
import nsw.revenue.coding.challenge.model.VehicleRegistration;
import nsw.revenue.coding.challenge.repository.OwnerRepository;
import nsw.revenue.coding.challenge.repository.VehicleRegistrationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UserControllerTest extends ComponentTestBase{

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private VehicleRegistrationRepository vehicleRegistrationRepository;


    @Test
    void doRegistration(){
        String key ="test1";
        ResponseEntity httpResponse = saveOwner(key);
        assertThat(httpResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        VehicleRegistration vehicleRegistration = getVehicleRegistration("");
        register(vehicleRegistration,key);
    }

    @Test
    void doRegistration_duplicateName(){
        String key ="test3";
        ResponseEntity httpResponse = saveOwner(key);
        assertThat(httpResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        httpResponse = saveOwner(key);
        assertThat(httpResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);

    }

    @Test
    void doRegistration_invalidPassword(){
        String key ="er";
        ResponseEntity httpResponse = saveOwner(key);
        assertThat(httpResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

    }

    @Test
    void getRegistration(){
        String key =  "test2";
        ResponseEntity ownerResponse = saveOwner(key);
        assertThat(ownerResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        VehicleRegistration vehicleRegistration =getVehicleRegistration("2");
        register(vehicleRegistration,key);

        HttpEntity entity = new HttpEntity<>( getHttpHeaders("test2"));
      ResponseEntity<Set<VehicleRegistration>> httpResponse =  testRestTemplate.exchange("/users/vehicles", HttpMethod.GET, entity,new ParameterizedTypeReference<Set<VehicleRegistration>>() {});

        assertThat(httpResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(httpResponse.getBody().contains(vehicleRegistration));
        vehicleRegistration = getVehicleRegistration("1");
         register(vehicleRegistration,key);
        httpResponse =  testRestTemplate.exchange("/users/vehicles", HttpMethod.GET, entity,new ParameterizedTypeReference<Set<VehicleRegistration>>() {});
        assertThat(httpResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(httpResponse.getBody().contains(vehicleRegistration));
    }

    private void register(VehicleRegistration vehicleRegistration,String key){
        HttpEntity entity = new HttpEntity<>(vehicleRegistration, getHttpHeaders(key));
        ResponseEntity httpResponse =  testRestTemplate.exchange("/users/vehicles/register", HttpMethod.POST, entity, VehicleRegistration.class);
        assertThat(httpResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

    }
    private VehicleRegistration getVehicleRegistration(String index){
        VehicleRegistration vehicleRegistration = new VehicleRegistration();
        vehicleRegistration.setPlateNumber("P40");
        vehicleRegistration.setVehicle(getVehicle(index));
        Insurer insurer = new Insurer();
        insurer.setCode("32");
        insurer.setName("Allianz");
        vehicleRegistration.setInsurer(insurer);
        Registration registration = new Registration();
        registration.setExpired(false);
        registration.setExpiryDate(OffsetDateTime.now());
        vehicleRegistration.setRegistration(registration);
        return  vehicleRegistration;
    }
    private  Vehicle getVehicle(String index){
        Vehicle vehicle = new Vehicle();
        vehicle.setType("Wagon"+index);
        vehicle.setMake("BMW"+index);
        vehicle.setModel("X4 M40i"+index);
        vehicle.setColour("Blue"+index);
        vehicle.setVin("12389347324"+index);
        vehicle.setTareWeight(1700l);
        vehicle.setGrossMass(null);
return vehicle;
    }

}
