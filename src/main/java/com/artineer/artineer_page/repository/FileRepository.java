package com.artineer.artineer_page.repository;

import com.artineer.artineer_page.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FileRepository extends JpaRepository<File, Long> {
}
