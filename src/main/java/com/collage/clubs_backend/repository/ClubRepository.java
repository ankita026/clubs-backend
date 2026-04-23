package com.collage.clubs_backend.repository;

import com.collage.clubs_backend.model.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ClubRepository extends JpaRepository<Club, Long> {
    List<Club> findByCategory(String category);
    List<Club> findByActiveTrue();
}