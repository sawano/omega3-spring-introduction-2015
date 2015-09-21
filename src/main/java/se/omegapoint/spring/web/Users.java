package se.omegapoint.spring.web;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import se.omegapoint.spring.users.User;

import java.util.List;

import static se.sawano.java.commons.lang.Validate.noNullElements;

public class Users extends ResourceSupport {

    public final List<Resource<User>> users;

    public Users(final List<Resource<User>> users) {
        this.users = noNullElements(users);
    }
}
