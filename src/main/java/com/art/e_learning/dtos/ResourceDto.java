package com.art.e_learning.dtos;

import com.art.e_learning.models.File;
import com.art.e_learning.models.Text;
import com.art.e_learning.models.Video;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public record ResourceDto<T> ( Integer id,
        String name,
        int size,
        String url,
        Integer lectureId,
        T inherited) {

    public static ResourceDto toResponse(DTOResourcesBase DTOResourcesBase){
        var inherited = DTOResourcesBase.getType() != null ? DTOResourcesBase.getType() : DTOResourcesBase.getContent() != null ? DTOResourcesBase.getContent() : DTOResourcesBase.getLength();
        Integer lectureId = DTOResourcesBase.getLecture() == null ? null : DTOResourcesBase.getLecture().getId();

        return new ResourceDto<>(DTOResourcesBase.getId(), DTOResourcesBase.getName(), DTOResourcesBase.getSize(), DTOResourcesBase.getUrl(), lectureId, inherited);
    }

    public static List<DTOResourcesBase> fromListToBaseEntity(List<Object> objects, String className){
        List <DTOResourcesBase> dtoResourcesBases;

        if(className.equals("File")){
            List<File> files = new ArrayList<>();
            objects.forEach(object -> files.add((File) object));
            dtoResourcesBases = files.stream().map(file -> new DTOResourcesBase(file.getId(), file.getName(),
                    file.getSize(), file.getUrl(), file.getLecture(),
                    null, null, file.getType())).toList();
        } else if (className.equals("Video")) {
            List<Video> videos = new ArrayList<>();
            objects.forEach(object -> videos.add((Video) object));
            dtoResourcesBases = videos.stream().map(video -> new DTOResourcesBase(video.getId(),video.getName(),
                    video.getSize(), video.getUrl(), video.getLecture(),
                    video.getLength(), null, null)).toList();
        }else{
            List<Text> texts = new ArrayList<>();
            objects.forEach(object -> texts.add((Text) object));
            dtoResourcesBases = texts.stream().map(text -> new DTOResourcesBase(text.getId(), text.getName(),
                    text.getSize(), text.getUrl(), text.getLecture(),
                    null, text.getContent(), null)).toList();
        }

        return dtoResourcesBases;
    }


    public static List<Object> toResponseList(List<DTOResourcesBase> resources){
        return Arrays.asList(resources.stream().map(entity -> {
            var inherited = entity.getType() != null ? entity.getType() :
                    entity.getContent() != null ? entity.getContent()
                            : entity.getLength();

            Integer lectureId = entity.getLecture() == null ? null : entity.getLecture().getId();

            return new ResourceDto<>(entity.getId(), entity.getName(), entity.getSize(), entity.getUrl(), lectureId, inherited);
        }).toArray());
    }
}
