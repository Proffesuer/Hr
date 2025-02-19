package com.ogoma.hr_core.boundaries.file_management.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "file_metadata")
public class FileMetadata implements Serializable {
    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "file_id")
    private File file;
    private String extension;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
