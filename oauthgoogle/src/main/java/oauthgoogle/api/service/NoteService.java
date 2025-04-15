package oauthgoogle.api.service;

import java.util.List;

import oauthgoogle.api.entity.Note;

public interface NoteService {

	Note createNoteForUser(String username,String content);
	Note updateNoteForUser(Long noteId,String content,String username);
	void deleteNoteForUser(Long noteId,String username);
	List<Note> getNoteForUser(String username);
}
