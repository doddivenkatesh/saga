package oauthgoogle.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import oauthgoogle.api.entity.Note;
import oauthgoogle.api.repository.NoteRepository;
import oauthgoogle.api.service.NoteService;

@Service
public class NoteServiceImpl implements NoteService{

	@Autowired
	private NoteRepository noteRepository;
	@Override
	public Note createNoteForUser(String username, String content) {
		
		Note note = new Note();
		note.setContenet(content);
		note.setOwnerUsername(username);
		Note savedNote = noteRepository.save(note);
		return savedNote;
	}

	@Override
	public Note updateNoteForUser(Long noteId, String content, String username) {
		Note note = noteRepository.findById(noteId).orElseThrow(() -> new RuntimeException("Note not found"));
		note.setContenet(content);
		Note updateNote = noteRepository.save(note);
		return updateNote;
	}

	@Override
	public void deleteNoteForUser(Long noteId, String username) {
		noteRepository.deleteById(noteId);
	}

	@Override
	public List<Note> getNoteForUser(String username) {
		List<Note> personalNotes = noteRepository.findByOwnerUsername(username);
		return personalNotes;
	}

}
