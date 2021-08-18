package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StorageService {
    private final FileMapper fileMapper;

    public StorageService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public List<File> getFilesByUserid(Integer userid) {
        return fileMapper.getFileListByUserid(userid);
    }

    public File getFileById(Integer fileId) {
        return fileMapper.getFile(fileId);
    }

    public Integer removeFile(Integer fileId) {
        return fileMapper.deleteFile(fileId);
    }

    public Boolean fileExists(File file) {
        return fileMapper.getFile(file.getFileId()) != null ||
                fileMapper.getFileByName(file.getFilename()) != null;
    }

    public Integer fileStore(File file) {
        return fileMapper.insertFile(file);
    }
}
