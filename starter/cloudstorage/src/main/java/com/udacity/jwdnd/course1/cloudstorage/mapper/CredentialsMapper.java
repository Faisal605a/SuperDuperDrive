package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialsMapper {


    @Select("Select * from CREDENTIALS WHERE  userId =#{userId}")
    List<Credentials> getAllCredentials(Integer userId);

    @Insert("INSERT INTO CREDENTIALS (credentialid, url, username, key, password,userid ) VALUES(#{credentialid}, #{url}, #{username}, #{key}, #{password},#{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    int insert(Credentials credentials);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialid}" )
    void delete(Integer credentialid);

    @Update("UPDATE CREDENTIALS SET url=#{url}, username=#{username},  key=#{key} ,password=#{password}, userid=#{userid}  WHERE credentialid =#{credentialid}")
    void update(Credentials Credentials);
}
