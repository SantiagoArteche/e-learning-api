package com.art.e_learning.dtos;

import com.art.e_learning.generic.InheritedBaseResource;
import com.art.e_learning.models.File;
import com.art.e_learning.models.Text;
import com.art.e_learning.models.Video;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public record InheritedBaseResourceDto<T> (Integer id,
                                           String name,
                                           int size,
                                           String url,
                                           Integer lectureId,
                                           T inherited
) {

    public static InheritedBaseResourceDto toResponse(InheritedBaseResource inheritedResourcesBaseDTO){
        var inherited = inheritedResourcesBaseDTO.getType() != null ? inheritedResourcesBaseDTO.getType() : inheritedResourcesBaseDTO.getContent() != null ? inheritedResourcesBaseDTO.getContent() : inheritedResourcesBaseDTO.getLength();
        Integer lectureId = inheritedResourcesBaseDTO.getLecture() == null ? null : inheritedResourcesBaseDTO.getLecture().getId();

        return new InheritedBaseResourceDto<>(inheritedResourcesBaseDTO.getId(), inheritedResourcesBaseDTO.getName(), inheritedResourcesBaseDTO.getSize(), inheritedResourcesBaseDTO.getUrl(), lectureId, inherited);
    }


    public static List<InheritedBaseResource> fromListToBaseEntity(List<Object> objects, String className){
        List <InheritedBaseResource> inheritedResourcesBaseDtos;

        if(className.equals("File")){
            List<File> files = new ArrayList<>();
            objects.forEach(object -> files.add((File) object));
            inheritedResourcesBaseDtos = files.stream().map(file -> new InheritedBaseResource(file.getId(), file.getName(),
                    file.getSize(), file.getUrl(), file.getLecture(),
                    null, null, file.getType())).toList();
        } else if (className.equals("Video")) {
            List<Video> videos = new ArrayList<>();
            objects.forEach(object -> videos.add((Video) object));
            inheritedResourcesBaseDtos = videos.stream().map(video -> new InheritedBaseResource(video.getId(),video.getName(),
                    video.getSize(), video.getUrl(), video.getLecture(),
                    video.getLength(), null, null)).toList();
        }else{
            List<Text> texts = new ArrayList<>();
            objects.forEach(object -> texts.add((Text) object));
            inheritedResourcesBaseDtos = texts.stream().map(text -> new InheritedBaseResource(text.getId(), text.getName(),
                    text.getSize(), text.getUrl(), text.getLecture(),
                    null, text.getContent(), null)).toList();
        }

        return inheritedResourcesBaseDtos;
    }


    public static List<Object> toListResponse(List<InheritedBaseResource> resources){
        return Arrays.asList(resources.stream().map(entity -> {
            var inherited = entity.getType() != null ? entity.getType() :
                    entity.getContent() != null ? entity.getContent()
                            : entity.getLength();

            Integer lectureId = entity.getLecture() == null ? null : entity.getLecture().getId();

            return new InheritedBaseResourceDto<>(entity.getId(), entity.getName(), entity.getSize(), entity.getUrl(), lectureId, inherited);
        }).toArray());
    }
}
