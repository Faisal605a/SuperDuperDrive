package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.DBFile;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DBFileMapper {

    @Select("SELECT * FROM FILES WHERE fileid = #{fileid}")
    DBFile getFile(Integer fileId);

    @Select("SELECT * FROM FILES WHERE userid = #{userid}")
    List<DBFile> getAllFile(Integer userid);
    @Insert("INSERT INTO FILES (fileid, filename, contenttype, filesize, userid, filedata) VALUES(#{fileid}, #{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileid")
    int save(DBFile dbFile);

    @Delete("DELETE FROM FILES WHERE fileid = #{fileid}" )
    void delete(Integer fileid);

    @Update("UPDATE FILES SET filename=#{filename}, contenttype=#{contenttype}, filesize=#{filesize}, userid=#{userid}, filedata=#{filedata}  WHERE fileid =#{fileid}")
    void update(DBFile dbFile);
}
