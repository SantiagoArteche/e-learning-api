package com.art.e_learning.repositories;

import com.art.e_learning.models.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Integer> {
}
