package se.omegapoint.spring.web;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest(value = {"server.port:0", "management.port:0"})
public class ApplicationIT {

    @Value("${local.server.port}")
    int serverPort;
    RestTemplate restTemplate = new RestTemplate();

    @Test
    public void should_get_api_entry_point() {
        final String response = restTemplate.getForObject(uriBuilder().build().toUri(), String.class);

        assertEquals("{\"links\":[{\"rel\":\"users\",\"href\":\"http://localhost:" + serverPort + "/users\"},{\"rel\":\"self\",\"href\":\"http://localhost:" + serverPort + "\"}]}", response);
    }

    @Test
    public void should_load_the_entire_web_application_and_try_to_get_a_resource_via_http() {
        final long userId = 1L;

        final URI uri = uriBuilder().path("users/{id}")
                                    .buildAndExpand(userId)
                                    .toUri();

        final UserDto response = restTemplate.getForObject(uri, UserDto.class);

        assertEquals(userId, response.id);
        assertEquals("donald", response.name);
        assertEquals(uri.toString(), response.getLink("self").getHref());
    }

    private UriComponentsBuilder uriBuilder() {
        return UriComponentsBuilder.fromHttpUrl("http://localhost")
                                   .port(serverPort);
    }

    static class UserDto extends ResourceSupport {
        public final String name;
        public final long id;

        @JsonCreator
        UserDto(@JsonProperty("name") final String name, @JsonProperty("id") final long id) {
            this.name = name;
            this.id = id;
        }
    }

}
