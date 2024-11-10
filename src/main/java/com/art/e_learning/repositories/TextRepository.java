package com.art.e_learning.repositories;

import com.art.e_learning.models.Text;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TextRepository extends JpaRepository<Text, Integer> {
}
