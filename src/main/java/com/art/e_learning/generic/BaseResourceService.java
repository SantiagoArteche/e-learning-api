package com.art.e_learning.generic;

import com.art.e_learning.dtos.InheritedBaseResourceDto;
import com.art.e_learning.models.File;
import com.art.e_learning.models.Lecture;
import com.art.e_learning.models.Text;
import com.art.e_learning.models.Video;
import com.art.e_learning.repositories.LectureRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

import static com.art.e_learning.dtos.InheritedBaseResourceDto.toResponse;
import static com.art.e_learning.dtos.InheritedBaseResourceDto.toListResponse;

public abstract class BaseResourceService<T> implements IBaseResourceService<T> {

    private final JpaRepository<T, Integer> repository;
    private final LectureRepository lectureRepository;

    public BaseResourceService(JpaRepository<T, Integer> repository, LectureRepository lectureRepository){
        this.repository = repository;
        this.lectureRepository = lectureRepository;
    }

    @Override
    public List<Object> getAll(String nameClass) {
        List<InheritedBaseResource> all = InheritedBaseResourceDto.fromListToBaseEntity(new ArrayList<>(this.repository.findAll()),
                nameClass);
        return toListResponse(all);
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
    public InheritedBaseResourceDto create(InheritedBaseResourceDto<T> entity, String className) {
        InheritedBaseResource resourceDto;
        Lecture lecture;

        if (entity.lectureId() != null) {
            Lecture findLecture = this.lectureRepository.findById(entity.lectureId()).orElse(null);

            if (findLecture == null) {
                resourceDto = new InheritedBaseResource();
                resourceDto.setId(-1);
                return toResponse(resourceDto);
            } else {
                lecture = findLecture;
            }
        } else {
            lecture = null;
        }

        switch (className) {
            case "File":
                InheritedBaseResourceDto<File> newEntityFile = (InheritedBaseResourceDto<File>) entity;
                String fileType = (newEntityFile.inherited() != null)
                        ? entity.inherited().toString()
                        : null;

                resourceDto = new InheritedBaseResource(null, newEntityFile.name(), newEntityFile.size(),
                        newEntityFile.url(), lecture, null, null, fileType);

                File fileToSave = new File(fileType);
                fileToSave.setLecture(lecture);
                fileToSave.setName(entity.name());
                fileToSave.setUrl(entity.url());
                fileToSave.setSize(entity.size());

                File fileSaved = (File) this.repository.save((T) fileToSave);
                resourceDto.setId(fileSaved.getId());
                break;

            case "Video":

                InheritedBaseResourceDto<Video> newEntityVideo = (InheritedBaseResourceDto<Video>) entity;
                int videoLength = (newEntityVideo.inherited() != null)
                        ? (int) entity.inherited()
                        : 0;

                resourceDto = new InheritedBaseResource(null, newEntityVideo.name(), newEntityVideo.size(),
                        newEntityVideo.url(), lecture, videoLength, null, null);

                Video videoToSave = new Video(videoLength);
                videoToSave.setLecture(lecture);
                videoToSave.setName(entity.name());
                videoToSave.setUrl(entity.url());
                videoToSave.setSize(entity.size());

                Video videoSaved = (Video) this.repository.save((T) videoToSave);
                resourceDto.setId(videoSaved.getId());
                break;

            default:
                InheritedBaseResourceDto<Text> newEntityText = (InheritedBaseResourceDto<Text>) entity;
                String textContent = (newEntityText.inherited() != null)
                        ? entity.inherited().toString()
                        : null;

                resourceDto = new InheritedBaseResource(null, newEntityText.name(), newEntityText.size(),
                        newEntityText.url(), lecture, null, textContent, null);

                Text textToSave = new Text(textContent);
                textToSave.setLecture(lecture);
                textToSave.setName(entity.name());
                textToSave.setUrl(entity.url());
                textToSave.setSize(entity.size());

                Text textSaved = (Text) this.repository.save((T) textToSave);
                resourceDto.setId(textSaved.getId());
                break;
        }

        return toResponse(resourceDto);
    }

    @Override
    public boolean delete(Integer id) {
        if(this.repository.findById(id).orElse(null) == null) return false;

        this.repository.deleteById(id);

        return true;
    }
}
