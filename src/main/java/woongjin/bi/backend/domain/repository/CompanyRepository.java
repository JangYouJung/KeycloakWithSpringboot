package woongjin.bi.backend.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import woongjin.bi.backend.domain.entity.Realm;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Realm, String> {
    Optional<Realm> findById(String id);
}
