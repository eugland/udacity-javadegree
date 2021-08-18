package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

/*
CREATE TABLE IF NOT EXISTS FILES (
    fileId INT PRIMARY KEY auto_increment,
    filename VARCHAR,
    contenttype VARCHAR,
    filesize VARCHAR,
    userid INT,
    filedata BLOB,
    foreign key (userid) references USERS(userid)
);
 */
@Mapper
public interface FileMapper {

    @Select("SELECT fileId, filename, contenttype, filesize FROM FILES WHERE userid = #{userid}")
    List<File> getFileListByUserid(Integer userid);

    @Select("SELECT fileId, filename, contenttype, filesize, userid, filedata " +
            "FROM FILES WHERE fileId = #{fileId}")
    File getFile(Integer fileId);

    @Insert("INSERT INTO FILES (userid, filename, contenttype, filesize, filedata) " +
            "VALUES (#{userid}, #{filename}, #{contenttype}, #{filesize}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    Integer insertFile(File file);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    Integer deleteFile(Integer fileId);
}
