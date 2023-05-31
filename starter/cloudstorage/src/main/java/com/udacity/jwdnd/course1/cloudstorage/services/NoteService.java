package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {


    private  NoteMapper noteRepoistory;

    private  UserMapper userMapper;
    public List<Note> notes;
    public NoteService(NoteMapper noteRepoistory, UserMapper userMapper, List<Note> note) {
        this.noteRepoistory = noteRepoistory;
        this.userMapper = userMapper;
         this.notes = note;
    }

    public void saveNotes(NoteForm noteForm, String userName){
        User user = userMapper.getUser(userName);
        noteRepoistory.insert(new Note(noteForm.getId(), noteForm.getTitle(), noteForm.getDescription(), user.getUserId()));

    }

    public void updateNote( NoteForm noteForm, String userName){
        User user = userMapper.getUser(userName);
        noteRepoistory.update(new Note(noteForm.getId(), noteForm.getTitle(), noteForm.getDescription(), user.getUserId()));
    }

    public List<Note> getNotes(String userName){
        User user = userMapper.getUser(userName);
        notes= noteRepoistory.getAllNotes(user.getUserId());
        return notes;
    }

    public void deleteNote( Integer noteId){
        noteRepoistory.delete(noteId);

    }

}
