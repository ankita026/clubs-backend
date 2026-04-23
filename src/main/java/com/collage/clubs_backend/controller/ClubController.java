package com.collage.clubs_backend.controller;

import com.collage.clubs_backend.model.Club;
import com.collage.clubs_backend.model.User;
import com.collage.clubs_backend.repository.UserRepository;
import com.collage.clubs_backend.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/clubs")
@CrossOrigin(origins = "*")
public class ClubController {

    @Autowired
    private ClubService clubService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<Club> getAllClubs() {
        return clubService.getAllClubs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Club> getClubById(@PathVariable Long id) {
        return clubService.getClubById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Club createClub(@RequestBody Club club) {
        return clubService.createClub(club);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Club> updateClub(
            @PathVariable Long id,
            @RequestBody Club clubDetails) {
        return ResponseEntity.ok(clubService.updateClub(id, clubDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClub(@PathVariable Long id) {
        clubService.deleteClub(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/category/{category}")
    public List<Club> getByCategory(@PathVariable String category) {
        return clubService.getClubsByCategory(category);
    }

    @PostMapping("/{clubId}/join/{userId}")
    public ResponseEntity<String> joinClub(
            @PathVariable Long clubId,
            @PathVariable Long userId) {
        Club club = clubService.getClubById(clubId)
                .orElseThrow(() -> new RuntimeException("Club not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.getClubs().add(club);
        club.setCurrentMembers(club.getCurrentMembers() + 1);
        userRepository.save(user);
        return ResponseEntity.ok("Joined club successfully!");
    }

    @PostMapping("/{clubId}/leave/{userId}")
    public ResponseEntity<String> leaveClub(
            @PathVariable Long clubId,
            @PathVariable Long userId) {
        Club club = clubService.getClubById(clubId)
                .orElseThrow(() -> new RuntimeException("Club not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.getClubs().remove(club);
        club.setCurrentMembers(Math.max(0, club.getCurrentMembers() - 1));
        userRepository.save(user);
        return ResponseEntity.ok("Left club successfully!");
    }
}