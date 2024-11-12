package com.art.e_learning.dtos;

import com.art.e_learning.models.Resource;

import java.util.List;

public record ResourceDto(Integer id, String name, Integer size, String url) {
    public static ResourceDto toResponse(Resource resource){
        return new ResourceDto(resource.getId(), resource.getName(), resource.getSize(), resource.getUrl());
    }

    public static List<ResourceDto> toListResponse(List<Resource> resources){
        return resources.stream().map(ResourceDto::toResponse).toList();
    }
}
