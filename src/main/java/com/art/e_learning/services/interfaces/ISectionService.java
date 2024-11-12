package com.art.e_learning.services.interfaces;

import com.art.e_learning.dtos.SectionDto;
import com.art.e_learning.models.Section;

import java.util.List;

public interface ISectionService {
    List<SectionDto> getAll();
    SectionDto getById(Integer id);
    SectionDto create(SectionDto sectionDto);
    boolean delete(Integer id);
}
