package com.udacity.jwdnd.course1.cloudstorage.model;

public class Note {
    private Integer noteid;
    private String notetitle;
    private String notedescription;
    private Integer userid;

    public Note(Integer noteid, String noteTitle, String noteDescription, Integer userid) {
        this.noteid = noteid;
        this.notetitle = noteTitle;
        this.notedescription = noteDescription;
        this.userid = userid;
    }

    public Integer getNoteId() {
        return noteid;
    }

    public void setNoteid(Integer noteid) {
        this.noteid = noteid;
    }

    public String getNoteTitle() {
        return notetitle;
    }

    public void setNotetitle(String notetitle) {
        this.notetitle = notetitle;
    }

    public String getNoteDescription() {
        return notedescription;
    }

    public void setNotedescription(String notedescription) {
        this.notedescription = notedescription;
    }

    public Integer getUserId() {
        return userid;
    }

    public void setUserId(Integer userid) {
        this.userid = userid;
    }
}
