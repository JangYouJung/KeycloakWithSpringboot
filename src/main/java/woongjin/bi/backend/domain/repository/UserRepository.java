package woongjin.bi.backend.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import woongjin.bi.backend.domain.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUsernameAndDeletedAtIsNull(String username);


}
