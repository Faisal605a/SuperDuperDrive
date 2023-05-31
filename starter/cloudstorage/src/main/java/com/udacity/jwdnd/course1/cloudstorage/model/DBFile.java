package com.udacity.jwdnd.course1.cloudstorage.model;

public class DBFile {
    private Integer fileid;
    private String filename;
    private String contenttype;
    private Long filesize;
    private Integer userid;
    private byte[] filedata;

    public DBFile(Integer id, String fileName, String contentType, Long fileSize, Integer userId, byte[] fileData) {
        this.fileid = id;
        this.filename = fileName;
        this.contenttype = contentType;
        this.filesize = fileSize;
        this.userid = userId;
        this.filedata = fileData;
    }

    public Integer getFileid() {
        return fileid;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setFileid(Integer fileid) {
        this.fileid = fileid;
    }

    public Integer getId() {
        return fileid;
    }

    public void setId(Integer id) {
        this.fileid = id;
    }

    public String getContenttype() {
        return contenttype;
    }

    public void setContenttype(String contenttype) {
        this.contenttype = contenttype;
    }

    public Long getFilesize() {
        return filesize;
    }

    public void setFilesize(Long filesize) {
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

    public void setFiledata(byte[] filedata) {
        this.filedata = filedata;
    }
}
