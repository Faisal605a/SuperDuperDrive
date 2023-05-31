package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Select("Select * from NOTES WHERE  userId =#{userId}")
    List<Note> getAllNotes(Integer userId);

    @Insert("INSERT INTO NOTES ( notetitle, notedescription, userid) VALUES(#{noteTitle}, #{noteDescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int insert(Note note);


    @Delete("DELETE FROM NOTES WHERE noteId = #{noteId}" )
    void delete(Integer noteId);

    @Update("UPDATE NOTES SET notetitle=#{noteTitle}, notedescription=#{noteDescription}, userid=#{userId}  WHERE noteid =#{noteId}")
    void update(Note note);
}
