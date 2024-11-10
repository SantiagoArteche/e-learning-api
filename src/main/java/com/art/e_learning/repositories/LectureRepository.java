package com.art.e_learning.repositories;

import com.art.e_learning.models.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRepository extends JpaRepository<Lecture, Integer> {
}
