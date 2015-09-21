package se.omegapoint.spring.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@SpringBootApplication
@RestController
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @RequestMapping
    public ResourceSupport option() {
        final Link users = linkTo(methodOn(UserRestService.class).allUsers()).withRel("users");
        final Link self = linkTo(Application.class).withSelfRel();

        final ResourceSupport response = new ResourceSupport();
        response.add(users);
        response.add(self);
        return response;
    }

}
