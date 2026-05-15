package com.study.mapper;

import com.study.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserMapper extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
<<<<<<< HEAD
}
=======
}
>>>>>>> cb0181847d65aa2475010e5d4d79cb286d531fa4
