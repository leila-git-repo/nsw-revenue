package nsw.revenue.coding.challenge.rest;

import static org.assertj.core.api.Assertions.assertThat;

import nsw.revenue.coding.challenge.model.Credential;
import nsw.revenue.coding.challenge.model.JWTToken;
import nsw.revenue.coding.challenge.util.JwtTokenUtil;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
@AutoConfigureWireMock(port = 3030)
@DirtiesContext

public class ComponentTestBase implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    protected TestRestTemplate testRestTemplate;




    protected ResponseEntity saveOwner(String key){
        Credential credential = new Credential();
        credential.setPassword(key);
        credential.setUsername(key);
        HttpEntity<Credential> entity = new HttpEntity<>(credential);
        return testRestTemplate.exchange("/users/", HttpMethod.POST, entity, Credential.class);
    }

    protected HttpHeaders getHttpHeaders(String key) {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(HttpHeaders.AUTHORIZATION,getJwtToken(key));
        return headers;
    }

    protected String getJwtToken(String key){
            Credential credential = new Credential();
            credential.setUsername(key);
            credential.setPassword(key);
            ResponseEntity<JWTToken> httpResponse = testRestTemplate.postForEntity("/accessToken",credential,JWTToken.class);
            assertThat(httpResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat( httpResponse.getBody().getToken()!=null);
        String s = "Bearer "+httpResponse.getBody().getToken();
        return s;
    }
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("Test is started ! ");
    }



}
