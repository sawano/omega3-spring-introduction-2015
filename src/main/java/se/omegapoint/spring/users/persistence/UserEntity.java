package se.omegapoint.spring.users.persistence;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@Entity
public class UserEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    protected UserEntity() {
        // Needed because Hibernate
    }

    public UserEntity(final String name) {
        this.name = notNull(name);
    }

    public Long id() {
        return id;
    }

    public String name() {
        return name;
    }
}
