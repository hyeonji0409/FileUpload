package com.artineer.artineer_page.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Table(name = "file")
@Entity
@Getter
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;

    private String originalFilename;
    private String savedFilename;
    private String savedPath;


    @Builder
    public File(Long id, String originalFilename, String savedFilename, String savedPath) {
        this.id = id;
        this.originalFilename = originalFilename;
        this.savedFilename = savedFilename;
        this.savedPath = savedPath;
    }
}
