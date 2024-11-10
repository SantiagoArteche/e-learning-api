package com.art.e_learning.controllers;




import com.art.e_learning.generic.BaseResourceController;
import com.art.e_learning.generic.BaseResourceService;
import com.art.e_learning.models.File;

import org.springframework.web.bind.annotation.*;




@RestController
@RequestMapping("/api/files")
public class FileController extends BaseResourceController<File> {
    public FileController(BaseResourceService<File> service) {
        super(service, "File");
    }
}
