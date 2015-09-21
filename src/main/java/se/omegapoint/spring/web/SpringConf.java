package se.omegapoint.spring.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import se.omegapoint.spring.users.UserService;
import se.omegapoint.spring.users.persistence.UserEntity;
import se.omegapoint.spring.users.persistence.UserRepository;

import javax.annotation.PostConstruct;

import static java.util.Arrays.asList;

@Configuration
@EntityScan(basePackageClasses = UserEntity.class)
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class SpringConf {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public UserService userService() {
        return new UserService(userRepository);
    }

    @PostConstruct
    void createTestData() {
        userRepository.save(asList(new UserEntity("donald"),
                                   new UserEntity("mickey")));
    }

}
