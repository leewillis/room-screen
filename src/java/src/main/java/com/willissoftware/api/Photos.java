package com.willissoftware.api;

import com.willissoftware.domain.Photo;
import com.willissoftware.service.PhotoLibrary;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * Created by Lee on 18/09/2016.
 */
@RestController
public class Photos {

    @Autowired
    PhotoLibrary photoLibrary;

    @RequestMapping(value = "/nextPicture", method = RequestMethod.GET)
    public Photo getRandomPhoto(HttpServletRequest request){

        int idx = new Random().nextInt(photoLibrary.getFiles().size());

        String file = photoLibrary.getFiles().get(idx);

        Photo ret = new Photo(file);
        ret.Load();

        System.out.printf("Returning picture %s\n", file);
        //ret.setFullFilename(file);

        return ret;
    }

}
