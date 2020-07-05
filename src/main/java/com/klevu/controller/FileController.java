package com.klevu.controller;

import com.klevu.service.OrderDataService;
import com.klevu.utils.CommonUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vivek on 3/7/20.
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    OrderDataService orderDataService;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @RequestMapping(value = "/upload",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity uploadFile(@RequestParam("dataFile") MultipartFile dataFile){
        ResponseEntity responseEntity;
        try {
            BufferedReader br;
            try {
                String line;
                InputStream is = dataFile.getInputStream();
                br = new BufferedReader(new InputStreamReader(is));
                List<String> orderDataList = new ArrayList<>();
                while ((line = br.readLine()) != null) {
                    try {
                        orderDataList.add(line);
                    }catch (Exception e){
                        e.printStackTrace();
                        System.out.println("Unable to store the record :"+line);
                    }
                }
                responseEntity = new ResponseEntity(CommonUtility.getSuccessResponse("File uploaded.", HttpStatus.CREATED), HttpStatus.CREATED);

                System.out.println("Data uploading...");
                taskExecutor.execute(() -> orderDataService.storeData(orderDataList));
            } catch (IOException e) {
                responseEntity = new ResponseEntity(CommonUtility.getErrorResponse("file","uploadFile", HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            e.printStackTrace();
            responseEntity = new ResponseEntity(CommonUtility.getErrorResponse("file","uploadFile", HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

}
