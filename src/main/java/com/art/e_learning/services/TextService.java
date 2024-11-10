package com.art.e_learning.services;

import com.art.e_learning.generic.BaseResourceService;
import com.art.e_learning.models.Text;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class TextService extends BaseResourceService<Text> {
    public TextService(JpaRepository<Text, Integer> repository) {
        super(repository);
    }
}