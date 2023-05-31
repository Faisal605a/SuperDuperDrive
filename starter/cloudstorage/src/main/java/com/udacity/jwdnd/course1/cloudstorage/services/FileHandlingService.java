package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.DBFileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.DBFile;
import com.udacity.jwdnd.course1.cloudstorage.model.FileInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class FileHandlingService
{

    @Autowired
    private DBFileMapper dbFileRepository;
    @Autowired
    private UserMapper userMapper;


//    public FileHandlingService(List<DBFile> dbFileList) {
//        this.dbFileList = dbFileList;
//    }

    public List<DBFile> getDbFileList(String userName){

        return  dbFileRepository.getAllFile(userMapper.getUser(userName).getUserId());

    }


    public void uploadDoc(MultipartFile multipartFile, String userName) throws Exception
    {
        try
        {

            Integer userId = userMapper.getUser(userName).getUserId();
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
           // FileInformation fileUploadSuccessData = new FileInformation();
            DBFile dbFile = new DBFile( null, fileName, multipartFile.getContentType(),multipartFile.getSize(),userId, multipartFile.getBytes());
            dbFileRepository.save(dbFile);
//            fileUploadSuccessData.setId(dbFile.getId());
//            fileUploadSuccessData.setFileName(dbFile.getFilename());


            //return fileUploadSuccessData;
        }
        catch(MaxUploadSizeExceededException e)
        {
            throw new Exception("File size too large. Max allowed size - 15 MB");
        }
        catch(Exception e)
        {
            throw new Exception("Error uploading multipartFile.");
        }
    }
    public boolean fileExist(MultipartFile multipartFile, String name){
        return getDbFileList(name).stream().anyMatch(e -> e.getFilename()==multipartFile.getOriginalFilename());

    }

    public boolean DeleteDoc( Integer fileid) throws Exception
    {
        try
        {
            dbFileRepository.delete(fileid);


            return true;
        }
        catch(MaxUploadSizeExceededException e)
        {
            throw new Exception("File size too large. Max allowed size - 15 MB");
        }
        catch(Exception e)
        {
            throw new Exception("Error uploading file.");
        }
    }

    public DBFile getFile(Integer fileId){
        return dbFileRepository.getFile(fileId);
    }
}