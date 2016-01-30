package se.omegapoint.spring.users;

import se.omegapoint.spring.users.persistence.UserEntity;
import se.omegapoint.spring.users.persistence.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;
import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class UserService {

    private final UserRepository userRepository;

    public UserService(final UserRepository userRepository) {
        this.userRepository = notNull(userRepository);
    }

    public List<User> users() {
        return userRepository.findAll().stream()
                             .map(entity -> new User(entity.name(), entity.id()))
                             .collect(collectingAndThen(toList(), Collections::unmodifiableList));
    }

    public Optional<User> user(final Long userId) {
        final UserEntity userEntity = userRepository.findOne(userId);
        return ofNullable(userEntity).map(e -> new User(e.name(), e.id()));
    }
}
