package com.art.e_learning.dtos;

import com.art.e_learning.generic.InheritedBaseResource;
import com.art.e_learning.models.File;
import com.art.e_learning.models.Lecture;
import com.art.e_learning.models.Text;
import com.art.e_learning.models.Video;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InheritedBaseResourceDtoTest {
    Text text = new Text("TestContent");
    File file = new File("TestFile");
    Video video = new Video(3);
    Lecture lecture = new Lecture();
    InheritedBaseResourceDto<Text> inheritedBaseResourceDtoText;
    InheritedBaseResourceDto<File> inheritedBaseResourceDtoFile;
    InheritedBaseResourceDto<Video> inheritedBaseResourceDtoVideo;
    InheritedBaseResource converter;

    {
        this.lecture.setId(100);
        this.text.setLecture(this.lecture);
        this.file.setLecture(this.lecture);
        this.video.setLecture(this.lecture);

        this.text.setId(1);
        this.text.setName("TestTextName");
        this.text.setSize(5);
        this.text.setUrl("TestUrl");

        this.file.setId(2);
        this.file.setName("TestFileName");
        this.file.setSize(6);
        this.file.setUrl("TestUrl");

    }


    @Test
    void toTextResponse(){
        this.converter = new InheritedBaseResource(text.getId(), text.getName(), text.getSize(), text.getUrl(),
                this.lecture, null, text.getContent(),null);
        this.inheritedBaseResourceDtoText = InheritedBaseResourceDto.toResponse(this.converter);

        assertEquals(this.inheritedBaseResourceDtoText.id(), this.text.getId());
        assertEquals(this.inheritedBaseResourceDtoText.name(), this.text.getName());
        assertEquals(this.inheritedBaseResourceDtoText.size(), this.text.getSize());
        assertEquals(this.inheritedBaseResourceDtoText.url(), this.text.getUrl());
        assertEquals(this.inheritedBaseResourceDtoText.lectureId(), this.text.getLecture().getId());
        assertNotNull(this.inheritedBaseResourceDtoText.inherited());
        assertEquals(this.inheritedBaseResourceDtoText.inherited(), this.text.getContent());

    }

    @Test
    void toFileResponse(){
        this.converter = new InheritedBaseResource(this.file.getId(), this.file.getName(), this.file.getSize(), this.file.getUrl(),
                this.lecture, null, null,this.file.getType());
        this.inheritedBaseResourceDtoFile = InheritedBaseResourceDto.toResponse(this.converter);

        assertEquals(this.inheritedBaseResourceDtoFile.id(), this.file.getId());
        assertEquals(this.inheritedBaseResourceDtoFile.name(), this.file.getName());
        assertEquals(this.inheritedBaseResourceDtoFile.size(), this.file.getSize());
        assertEquals(this.inheritedBaseResourceDtoFile.url(), this.file.getUrl());
        assertEquals(this.inheritedBaseResourceDtoFile.lectureId(), this.file.getLecture().getId());
        assertNotNull(this.inheritedBaseResourceDtoFile.inherited());
        assertEquals(this.inheritedBaseResourceDtoFile.inherited(), this.file.getType());
    }

    @Test
    void toVideoResponse(){
        this.converter = new InheritedBaseResource(this.video.getId(), this.video.getName(), this.video.getSize(), this.video.getUrl(),
                this.lecture, this.video.getLength(), null, null);
        this.inheritedBaseResourceDtoFile = InheritedBaseResourceDto.toResponse(this.converter);

        assertEquals(this.inheritedBaseResourceDtoFile.id(), this.video.getId());
        assertEquals(this.inheritedBaseResourceDtoFile.name(), this.video.getName());
        assertEquals(this.inheritedBaseResourceDtoFile.size(), this.video.getSize());
        assertEquals(this.inheritedBaseResourceDtoFile.url(), this.video.getUrl());
        assertEquals(this.inheritedBaseResourceDtoFile.lectureId(), this.video.getLecture().getId());
        assertNotNull(this.inheritedBaseResourceDtoFile.inherited());
        assertEquals(this.inheritedBaseResourceDtoFile.inherited(), this.video.getLength());
    }

    @Test
    void fromTextListToBaseEntity(){
        List<Text> textList = new ArrayList<>();
        textList.add(this.text);
        List<InheritedBaseResource> textListFromBaseEntity = InheritedBaseResourceDto.fromListToBaseEntity(Arrays.asList(textList.toArray()), "Text");

        assertEquals(textListFromBaseEntity.size(), textList.size());
        assertEquals(textListFromBaseEntity.getFirst().getName(), textList.getFirst().getName());
        assertEquals(textListFromBaseEntity.getFirst().getSize(), textList.getFirst().getSize());
        assertEquals(textListFromBaseEntity.getFirst().getUrl(), textList.getFirst().getUrl());
        assertEquals(textListFromBaseEntity.getFirst().getLecture().getId(), textList.getFirst().getLecture().getId());
        assertNotNull(textListFromBaseEntity.getFirst().getContent());
        assertEquals(textListFromBaseEntity.getFirst().getContent(), textList.getFirst().getContent());
    }

    @Test
    void fromVideoListToBaseEntity(){
        List<Video> videoList = new ArrayList<>();
        videoList.add(this.video);
        List<InheritedBaseResource> videoListFromBaseEntity = InheritedBaseResourceDto.fromListToBaseEntity(Arrays.asList(videoList.toArray()), "Video");

        assertEquals(videoListFromBaseEntity.size(), videoList.size());
        assertEquals(videoListFromBaseEntity.getFirst().getName(), videoList.getFirst().getName());
        assertEquals(videoListFromBaseEntity.getFirst().getSize(), videoList.getFirst().getSize());
        assertEquals(videoListFromBaseEntity.getFirst().getUrl(), videoList.getFirst().getUrl());
        assertEquals(videoListFromBaseEntity.getFirst().getLecture().getId(), videoList.getFirst().getLecture().getId());
        assertNotNull(videoListFromBaseEntity.getFirst().getLength());
        assertEquals(videoListFromBaseEntity.getFirst().getLength(), videoList.getFirst().getLength());
    }

    @Test
    void fromFileListToBaseEntity(){
        List<File> fileList = new ArrayList<>();
        fileList.add(this.file);
        List<InheritedBaseResource> videoListFromBaseEntity = InheritedBaseResourceDto.fromListToBaseEntity(Arrays.asList(fileList.toArray()), "File");

        assertEquals(videoListFromBaseEntity.size(), fileList.size());
        assertEquals(videoListFromBaseEntity.getFirst().getName(), fileList.getFirst().getName());
        assertEquals(videoListFromBaseEntity.getFirst().getSize(), fileList.getFirst().getSize());
        assertEquals(videoListFromBaseEntity.getFirst().getUrl(), fileList.getFirst().getUrl());
        assertEquals(videoListFromBaseEntity.getFirst().getLecture().getId(), fileList.getFirst().getLecture().getId());
        assertNotNull(videoListFromBaseEntity.getFirst().getType());
        assertEquals(videoListFromBaseEntity.getFirst().getType(), fileList.getFirst().getType());
    }

    @Test
    void toTextListResponse(){
        List<Text> textList = new ArrayList<>();
        textList.add(this.text);
        List<Object> textListResponse = InheritedBaseResourceDto.toListResponse(
                InheritedBaseResourceDto.fromListToBaseEntity(
                        Arrays.asList(textList.toArray()), "Text"));

        assertEquals(textListResponse.size(), textList.size());
        assertEquals(((InheritedBaseResourceDto<Text>)textListResponse.getFirst()).name(), textList.getFirst().getName());
        assertEquals(((InheritedBaseResourceDto<Text>)textListResponse.getFirst()).size(), textList.getFirst().getSize());
        assertEquals(((InheritedBaseResourceDto<Text>)textListResponse.getFirst()).url(), textList.getFirst().getUrl());
        assertEquals(((InheritedBaseResourceDto<Text>)textListResponse.getFirst()).lectureId(), textList.getFirst().getLecture().getId());
        assertNotNull(((InheritedBaseResourceDto<Text>)textListResponse.getFirst()).inherited());
        assertEquals(((InheritedBaseResourceDto<Text>)textListResponse.getFirst()).inherited(), textList.getFirst().getContent());
    }

    @Test
    void toVideoListResponse(){
        List<Video> videoList = new ArrayList<>();
        videoList.add(this.video);
        List<Object> textListResponse = InheritedBaseResourceDto.toListResponse(
                InheritedBaseResourceDto.fromListToBaseEntity(
                        Arrays.asList(videoList.toArray()), "Video"));

        assertEquals(textListResponse.size(), videoList.size());
        assertEquals(((InheritedBaseResourceDto<Text>)textListResponse.getFirst()).name(), videoList.getFirst().getName());
        assertEquals(((InheritedBaseResourceDto<Text>)textListResponse.getFirst()).size(), videoList.getFirst().getSize());
        assertEquals(((InheritedBaseResourceDto<Text>)textListResponse.getFirst()).url(), videoList.getFirst().getUrl());
        assertEquals(((InheritedBaseResourceDto<Text>)textListResponse.getFirst()).lectureId(), videoList.getFirst().getLecture().getId());
        assertNotNull(((InheritedBaseResourceDto<Text>)textListResponse.getFirst()).inherited());
        assertEquals(((InheritedBaseResourceDto<Text>)textListResponse.getFirst()).inherited(), videoList.getFirst().getLength());
    }

    @Test
    void toFileListResponse(){
        List<File> fileList = new ArrayList<>();
        fileList.add(this.file);
        List<Object> textListResponse = InheritedBaseResourceDto.toListResponse(
                InheritedBaseResourceDto.fromListToBaseEntity(
                        Arrays.asList(fileList.toArray()), "File"));

        assertEquals(textListResponse.size(), fileList.size());
        assertEquals(((InheritedBaseResourceDto<Text>)textListResponse.getFirst()).name(), fileList.getFirst().getName());
        assertEquals(((InheritedBaseResourceDto<Text>)textListResponse.getFirst()).size(), fileList.getFirst().getSize());
        assertEquals(((InheritedBaseResourceDto<Text>)textListResponse.getFirst()).url(), fileList.getFirst().getUrl());
        assertEquals(((InheritedBaseResourceDto<Text>)textListResponse.getFirst()).lectureId(), fileList.getFirst().getLecture().getId());
        assertNotNull(((InheritedBaseResourceDto<Text>)textListResponse.getFirst()).inherited());
        assertEquals(((InheritedBaseResourceDto<Text>)textListResponse.getFirst()).inherited(), fileList.getFirst().getType());
    }
}
