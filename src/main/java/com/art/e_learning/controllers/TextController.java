package com.art.e_learning.controllers;


import com.art.e_learning.generic.BaseResourceController;
import com.art.e_learning.generic.BaseResourceService;
import com.art.e_learning.models.Text;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/texts")
public class TextController extends BaseResourceController<Text> {
    public TextController(BaseResourceService<Text> baseResourceService){
        super(baseResourceService, "Text");
    }
}
