package com.study.mapper;

import com.study.entity.GlobalProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface GlobalProfileMapper extends JpaRepository<GlobalProfile, Long> {
    Optional<GlobalProfile> findByUserId(Long userId);
}
