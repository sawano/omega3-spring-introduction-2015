package se.omegapoint.spring.users;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class User {

    public final Long id;
    public final String name;

    public User(final String name, final Long id) {
        this.id = notNull(id);
        this.name = notNull(name);
    }
}
