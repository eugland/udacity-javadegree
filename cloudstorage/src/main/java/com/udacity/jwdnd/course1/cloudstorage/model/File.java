package com.udacity.jwdnd.course1.cloudstorage.model;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.Arrays;

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
public class File {
    private Integer fileId;
    private String filename;
    private String contenttype;
    private long filesize; // or use int?
    private Integer userid;
    private byte[] filedata;

    public File(Integer fileId, String filename, String contenttype, long filesize, Integer userid, byte[] filedata) {
        this.fileId = fileId;
        this.filename = filename;
        this.contenttype = contenttype;
        this.filesize = filesize;
        this.userid = userid;
        this.filedata = filedata;
    }

    public File(Integer fileId, String filename, String contenttype, long filesize) {
        this.fileId = fileId;
        this.filename = filename;
        this.contenttype = contenttype;
        this.filesize = filesize;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContenttype() {
        return contenttype;
    }

    public void setContenttype(String contenttype) {
        this.contenttype = contenttype;
    }

    public long getFilesize() {
        return filesize;
    }

    public void setFilesize(long filesize) {
        this.filesize = filesize;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public byte[] getFiledata() {
        return filedata;
    }

    public void setFiledata(byte[] filedata) { // TODO should I throw SQL exception?
        this.filedata = filedata;
    }

    @Override
    public String toString() {
        return "Files{" +
                "fileId=" + fileId +
                ", filename='" + filename + '\'' +
                ", contenttype='" + contenttype + '\'' +
                ", filesize='" + filesize + '\'' +
                ", userid=" + userid +
                // ", filedata=" + Arrays.toString(filedata) +
                '}';
    }
}
