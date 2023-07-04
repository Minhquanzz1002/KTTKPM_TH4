package vn.edu.iuh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.iuh.entity.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
}
