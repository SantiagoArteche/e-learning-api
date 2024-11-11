package com.art.e_learning.generic;

import com.art.e_learning.dtos.InheritedBaseResource;
import com.art.e_learning.dtos.InheritedBaseResourceDto;
import com.art.e_learning.models.File;
import com.art.e_learning.models.Text;
import com.art.e_learning.models.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

import static com.art.e_learning.dtos.InheritedBaseResourceDto.toResponse;
import static com.art.e_learning.dtos.InheritedBaseResourceDto.toResponseList;

public abstract class BaseResourceService<T> implements IBaseResourceService<T> {

    private final JpaRepository<T, Integer> repository;

    public BaseResourceService(JpaRepository<T, Integer> repository){
        this.repository = repository;
    }

    @Override
    public List<Object> getAll(String nameClass) {
        List<InheritedBaseResource> all = InheritedBaseResourceDto.fromListToBaseEntity(new ArrayList<>(this.repository.findAll()),
                nameClass);
        return toResponseList(all);
    }

    @Override
    public InheritedBaseResourceDto getById(Integer id, String nameClass) {
        T findEntity = this.repository.findById(id).orElse(null);

        InheritedBaseResourceDto inheritedBaseResourceDto = null;

        if(nameClass.equals("File")){
            File findFile = (File) findEntity;
            if(findFile != null){
                inheritedBaseResourceDto = toResponse(new InheritedBaseResource(findFile.getId(), findFile.getName(),
                        findFile.getSize(), findFile.getUrl(),
                        findFile.getLecture(), null,
                        null , findFile.getType()));
            }
        } else if (nameClass.equals("Video")) {
            Video findVideo = (Video) findEntity;
            if(findVideo != null){
                inheritedBaseResourceDto = toResponse(new InheritedBaseResource(findVideo.getId(), findVideo.getName(),
                        findVideo.getSize(), findVideo.getUrl(),
                        findVideo.getLecture(), findVideo.getLength(),
                        null , null));
            }
        }else{
            Text findText = (Text) findEntity;
            if(findText != null){
                inheritedBaseResourceDto = toResponse(new InheritedBaseResource(findText.getId(), findText.getName(),
                        findText.getSize(), findText.getUrl(),
                        findText.getLecture(), null,
                        findText.getContent() , null));
            }

        }

        return inheritedBaseResourceDto;
    }

    @Override
    public InheritedBaseResourceDto create(T entity, String className) {
        InheritedBaseResource resourceDto;
        entity = this.repository.save(entity);

        if(className.equals("File")){
            File newEntity = (File) entity;
            resourceDto = new InheritedBaseResource(newEntity.getId(), newEntity.getName(), newEntity.getSize(),
                    newEntity.getUrl(), newEntity.getLecture(), null, null , newEntity.getType());
        } else if (className.equals("Video")) {
            Video newEntity = (Video) entity;
            resourceDto = new InheritedBaseResource(newEntity.getId(), newEntity.getName(), newEntity.getSize(),
                    newEntity.getUrl(), newEntity.getLecture(), newEntity.getLength(), null , null);
        }else{
            Text newEntity = (Text) entity;
            resourceDto = new InheritedBaseResource(newEntity.getId(), newEntity.getName(), newEntity.getSize(),
                    newEntity.getUrl(), newEntity.getLecture(), null, newEntity.getContent() , null);
        }

        return toResponse(new InheritedBaseResource(resourceDto.getId(), resourceDto.getName(), resourceDto.getSize(),
                resourceDto.getUrl(), resourceDto.getLecture(), null, resourceDto.getContent() , null));
    }

    @Override
    public boolean delete(Integer id) {
        if(this.repository.findById(id).orElse(null) == null) return false;

        this.repository.deleteById(id);

        return true;
    }
}
