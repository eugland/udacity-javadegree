package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CredentialService {
    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public List<Credential> getCredentialsByUserid(Integer userid) {
        // key could be null... handles item?
        return credentialMapper.getCredentialsByUserid(userid).parallelStream()
                .map(cr -> cr.setDecryptedPass(
                        encryptionService.decryptValue(cr.getPassword(), cr.getKey())))
                .collect(Collectors.toList());
    }

    public Credential getCredential(Integer credentialid) {
        Credential c = credentialMapper.getCredential(credentialid);
        return c.setDecryptedPass(encryptionService.decryptValue(c.getPassword(), c.getKey()));
    }

    // TODO assume Credential new password is in decrypted
    public Integer updateCredential(Credential credential) {
        Credential og = getCredential(credential.getCredentialid());
        credential.setPassword(encryptionService.encryptValue(credential.getDecryptedPass(), og.getKey()));
        return credentialMapper.updateCredential(credential);
    }

    public Integer addCredential(Credential credential) {

        SecureRandom ra = new SecureRandom();
        byte[] key = new byte[16];
        ra.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getDecryptedPass(), encodedKey);
        credential.setPassword(encryptedPassword);
        credential.setKey(encodedKey);
        // System.out.println(credential);
        return credentialMapper.insertCredential(credential);
    }

    public Integer deleteCredential(Integer credentialid) {
        System.out.println("Deleting: credential" + credentialid);
        return credentialMapper.delete(credentialid);
    }


}
