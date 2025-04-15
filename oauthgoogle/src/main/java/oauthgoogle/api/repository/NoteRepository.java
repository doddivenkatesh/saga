package oauthgoogle.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import oauthgoogle.api.entity.Note;

public interface NoteRepository extends JpaRepository<Note, Long>{

	List<Note> findByOwnerUsername(String ownerUsername);
}
