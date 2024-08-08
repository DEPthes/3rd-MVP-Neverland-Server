package depth.mvp.thinkerbell.domain.user.repository;

import depth.mvp.thinkerbell.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
