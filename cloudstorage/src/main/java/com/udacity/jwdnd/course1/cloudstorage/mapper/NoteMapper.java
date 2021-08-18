package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

/*
    private Integer noteid;
    private String notetitle;
    private String notedescription;
    private Integer userid;
 */
@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM NOTES WHERE noteid = #{noteid}")
    Note getNoteById(Integer noteid);

    @Select("SELECT * FROM NOTES WHERE userid = #{userid} ORDER BY noteid DESC")
    List<Note> getNotesByUserid(Integer userid);

    @Update("UPDATE NOTES SET notetitle = #{notetitle}, " +
            "notedescription = #{notedescription} " +
            "WHERE noteid = #{noteid} AND userid = #{userid}")
    Integer update(Note note);


    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) " +
            "VALUES (#{notetitle}, #{notedescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    Integer insertNote(Note note);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteid} AND userid = #{userid}")
    Integer removeNote(Note note);

    @Delete("DELETE FROM NOTES WHERE noteid = #{id}")
    Integer deleteNote(Integer id);
}
