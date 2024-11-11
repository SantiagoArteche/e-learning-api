package com.art.e_learning.controllers;


import com.art.e_learning.generic.BaseResourceController;
import com.art.e_learning.generic.BaseResourceService;
import com.art.e_learning.models.Video;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resources/videos")
public class VideoController extends BaseResourceController<Video> {
    public VideoController(BaseResourceService<Video> service) {
        super(service, "Video");
    }
}
