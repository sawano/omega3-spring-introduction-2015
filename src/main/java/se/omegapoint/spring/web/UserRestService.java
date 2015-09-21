package se.omegapoint.spring.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.omegapoint.spring.users.User;
import se.omegapoint.spring.users.UserService;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.status;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static se.sawano.java.commons.lang.Validate.notNull;

@RestController
@RequestMapping("/users")
public class UserRestService {

    private final UserService userService;

    @Autowired
    public UserRestService(final UserService userService) {
        this.userService = notNull(userService);
    }

    @RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE)
    public Users allUsers() {
        final Users users = userService.users().stream()
                                       .map(UserRestService::withRels)
                                       .collect(collectingAndThen(toList(), Users::new));
        return withRels(users);
    }

    @RequestMapping(value = "/{userId}", method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Resource<User>> user(@PathVariable final Long userId) {
        notNull(userId);

        return userService.user(userId)
                          .map(UserRestService::withRels)
                          .map(UserRestService::withNextRelToStart)
                          .map(ResponseEntity::ok)
                          .orElse(status(NOT_FOUND).body(null));
    }

    private static Users withRels(final Users users) {
        users.add(linkTo(UserRestService.class).withSelfRel());
        return withNextRelToStart(users);
    }

    private static Resource<User> withRels(final User user) {
        final Link self = linkTo(methodOn(UserRestService.class).user(user.id)).withSelfRel();
        return new Resource<>(user, self);
    }

    private static <T extends ResourceSupport> T withNextRelToStart(final T resource) {
        resource.add(linkTo(Application.class).withRel("next"));
        return resource;
    }

}
