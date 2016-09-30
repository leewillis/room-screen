/**
 * Created by willisl on 20/09/2016.
 */

package com.willissoftware.domain;

import javaxt.io.Image;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.codec.binary.Base64;


public class Photo {

    private static final Logger logger = LoggerFactory.getLogger(Photo.class);

    private String fullFileName;
    private String fileName;
    private String imageAsB64;
    private int width;
    private int height;
    private boolean landscape;
    private float ratio;
    private String label;
    private String exifDate;

    public Photo(String fullFileName) {
        this.fullFileName = fullFileName;
        this.fileName = fullFileName.substring(fullFileName.lastIndexOf(File.separator)+1);
    }

    private boolean loaded = false;

    public void Load() {

        if(loaded){
            return;
        }
        //get the width and height
        File file = new File(fullFileName);
        try {
            BufferedImage image = ImageIO.read(file);
            //todo get the EXIF data and grab the taken date/time
            width = image.getWidth();
            height = image.getHeight();
            landscape = width > height;
            ratio = (float) width / (float) height;

            imageAsB64 = Base64.encodeBase64String(FileUtils.readFileToByteArray(file));

            //todo test this!
/*
            if(fullFileName.toLowerCase().endsWith(".jpg")){
                imageAsB64 = "data:image/jpeg;base64," + imageAsB64;
            }else if(fullFileName.toLowerCase().endsWith(".png")){
                imageAsB64 = "data:image/png;base64," + imageAsB64;
            }
*/

            Image xtimg = new Image(fullFileName);
            HashMap<Integer, Object> exif = xtimg.getExifTags();
            exifDate  = (String) exif.get(0x0132);

            this.label = (this.exifDate==null ? "" :  " - " + this.exifDate);

            loaded = true;
        } catch (IOException e) {
            loaded = false;
            e.printStackTrace();
        }

    }

    public String getFullFileName() {
        return fullFileName;
    }

    public void setFullFileName(String fullFileName) {
        this.fullFileName = fullFileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getExifDate() {
        return exifDate;
    }

    public void setExifDate(String exifDate) {
        this.exifDate = exifDate;
    }

    public String getImageAsB64() {
        return imageAsB64;
    }

    public void setImageAsB64(String imageAsB64) {
        this.imageAsB64 = imageAsB64;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isLandscape() {
        return landscape;
    }

    public void setLandscape(boolean landscape) {
        this.landscape = landscape;
    }

    public float getRatio() {
        return ratio;
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
    }
}
