package com.passlocker.passlocker.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "folders")
public class Folder {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String folderId;

    private String folderNamer;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updated_at;

    public Folder() {}

    public Folder(String folderId, String folderNamer, Date created_at, Date updated_at) {
        this.folderId = folderId;
        this.folderNamer = folderNamer;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public String getFolderId() {
        return folderId;
    }

    public Folder setFolderId(String folderId) {
        this.folderId = folderId;
        return this;
    }

    public String getFolderNamer() {
        return folderNamer;
    }

    public Folder setFolderNamer(String folderNamer) {
        this.folderNamer = folderNamer;
        return this;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public Folder setCreated_at(Date created_at) {
        this.created_at = created_at;
        return this;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public Folder setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
        return this;
    }
}
