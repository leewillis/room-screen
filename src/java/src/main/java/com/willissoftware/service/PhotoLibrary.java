package com.willissoftware.service;

import com.willissoftware.domain.Photo;
import org.slf4j.ILoggerFactory;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.LogManager;
import java.util.stream.Stream;

/**
 * Created by Lee on 14/09/2016.
 */
@Service
public class PhotoLibrary {

    //private static logger = LoggerFactory.
    @Value("${photo.folder}")
    private String folder;
    private Queue<String> folders;
    private List<String> files;
    private Map<String, String> extensions;

    @PostConstruct
    public void Initialise(){
        //this.folder = folder;

        folders = new LinkedList<>();
        files = new ArrayList<>();
        extensions =  new HashMap<>();

        extensions.put(".jpg", ".jpg");
        extensions.put(".png", ".png");
        folders.add(folder);

        int count = 0;
        while(!folders.isEmpty()){
            count += getFiles(folders.remove(), extensions);
        }
        System.out.println("Found "  + count + " pictures");
    }

    private int getFiles(String folder, Map<String, String> extensions){
        int count = 0;

        FileFilter ff = pathname -> {
            if(pathname.isDirectory()){
                return true;
            }
            if(!pathname.getName().contains(".")){
                return false;
            }
            String ext = pathname.getName().substring(pathname.getName().lastIndexOf(".")).toLowerCase();
            return extensions.containsKey(ext);
        };
        File[] listfiles = new File(folder).listFiles(ff);
        if(listfiles==null){
            return 0;
        }
        for (File file : listfiles) {
            if (file.isDirectory()) {
                //System.out.println("Directory: " + file.getName());
                folders.add(file.getAbsolutePath());
            } else {
                files.add(file.getAbsolutePath());
                //System.out.println("File: " + file.getAbsolutePath());
                count++;
            }
        }
        return count;
    }

    public List<String> getFiles() {
        return files;
    }
}
