package se.omegapoint.spring.web.persistence;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import se.omegapoint.spring.users.persistence.UserEntity;
import se.omegapoint.spring.users.persistence.UserRepository;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = UserRepositoryIT.Conf.class)
@Transactional
public class UserRepositoryIT {

    @Autowired
    UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        userRepository.save(asList(new UserEntity("donald"), new UserEntity("mickey")));
    }

    @Test
    public void should_get_all_users() {
        final List<UserEntity> all = userRepository.findAll();

        assertEquals(2, all.size());
    }

    @Test
    public void should_find_by_name_ignore_case() {
        final List<UserEntity> donalds = userRepository.findByNameIgnoreCase("Donald");

        assertEquals(1, donalds.size());
        assertEquals("donald", donalds.get(0).name());
    }

    @Test
    public void should_find_by_name() {
        final List<UserEntity> donalds = userRepository.findByName("donald");

        assertEquals(1, donalds.size());
        assertEquals("donald", donalds.get(0).name());
    }

    @Test
    public void should_not_find_when_wrong_case() {
        final List<UserEntity> donalds = userRepository.findByName("Donald");

        assertTrue(donalds.isEmpty());
    }

    @Configuration
    @EnableAutoConfiguration
    @EntityScan(basePackageClasses = UserEntity.class)
    @EnableJpaRepositories(basePackageClasses = UserRepository.class)
    static class Conf {}
}
