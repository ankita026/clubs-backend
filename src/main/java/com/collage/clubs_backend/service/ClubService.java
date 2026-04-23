package com.collage.clubs_backend.service;

import com.collage.clubs_backend.model.Club;
import com.collage.clubs_backend.repository.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ClubService {

    @Autowired
    private ClubRepository clubRepository;

    public List<Club> getAllClubs() {
        return clubRepository.findAll();
    }

    public Optional<Club> getClubById(Long id) {
        return clubRepository.findById(id);
    }

    public Club createClub(Club club) {
        club.setActive(true);
        club.setCurrentMembers(0);
        return clubRepository.save(club);
    }

    public Club updateClub(Long id, Club clubDetails) {
        Club club = clubRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Club not found"));
        club.setName(clubDetails.getName());
        club.setDescription(clubDetails.getDescription());
        club.setCategory(clubDetails.getCategory());
        club.setPresidentName(clubDetails.getPresidentName());
        club.setMaxMembers(clubDetails.getMaxMembers());
        return clubRepository.save(club);
    }

    public void deleteClub(Long id) {
        clubRepository.deleteById(id);
    }

    public List<Club> getClubsByCategory(String category) {
        return clubRepository.findByCategory(category);
    }
}