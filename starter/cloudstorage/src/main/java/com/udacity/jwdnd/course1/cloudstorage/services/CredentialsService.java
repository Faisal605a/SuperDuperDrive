package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialsService {


    private CredentialsMapper credentialsMapper;
//    private HashService hashService;
    private EncryptionService encryptionService;

    private UserMapper userMapper;
    public List<Credentials> credentials;

    public CredentialsService(CredentialsMapper credentialsMapper, UserMapper userMapper, List<Credentials>c,EncryptionService encryptionService ) {
        this.credentialsMapper = credentialsMapper;
        this.userMapper = userMapper;
        this.credentials = c;
        this.encryptionService = encryptionService;
    }
    public void InsertCredentials(Credentials credentials, String userName){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = encryptionService.encryptValue(credentials.getPassword(), encodedSalt);

        credentials.setKey(encodedSalt);
        credentials.setPassword(hashedPassword);
        credentials.setUserid(userMapper.getUser(userName).getUserId());


        credentialsMapper.insert(credentials);

    }
    public void updateCredentials(Credentials credentials, String userName){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);

        String hashedPassword = encryptionService.encryptValue(credentials.getPassword(), encodedSalt);

        credentials.setKey(encodedSalt);
        credentials.setPassword(hashedPassword);

        credentials.setUserid(userMapper.getUser(userName).getUserId());

        credentialsMapper.update(credentials);

    }
    public void deleteCredential(Integer credentialId){
        credentialsMapper.delete(credentialId);
    }

    public List<Credentials> getAllCredentials(String userName){

        credentials=credentialsMapper.getAllCredentials(userMapper.getUser(userName).getUserId());
        return credentialsMapper.getAllCredentials(userMapper.getUser(userName).getUserId());
    }

}
