package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoteService {
    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public List<Note> getNotesByUserid(Integer userid) {
        return noteMapper.getNotesByUserid(userid);
    }

    public Note getNoteById(Integer noteid) {
        return noteMapper.getNoteById(noteid);
    }

    public Integer add(Note note) {
        if (note.getNoteid() != null)
            return noteMapper.update(note);
        else
            return noteMapper.insertNote(note);
    }

    public Integer delete(Note note) {
        return noteMapper.removeNote(note);
    }

    public Integer delete(Integer id) {
        return noteMapper.deleteNote(id);
    }

    //TODO implemment noteservice
}
