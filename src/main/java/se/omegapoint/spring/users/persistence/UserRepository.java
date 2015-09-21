package se.omegapoint.spring.users.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    List<UserEntity> findByName(String name);

    List<UserEntity> findByNameIgnoreCase(String name);

}
