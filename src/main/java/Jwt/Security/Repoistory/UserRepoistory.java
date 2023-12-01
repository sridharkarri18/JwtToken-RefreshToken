package Jwt.Security.Repoistory;

import Jwt.Security.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepoistory extends JpaRepository<User,Integer> {

    Optional<User> findByusername(String username);
}
