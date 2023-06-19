package com.example.factorio.fasada;

import com.dropbox.core.DbxException;
import com.example.factorio.mediator.DropBoxMediator;
import com.example.factorio.model.DropBoxFile;
import com.example.factorio.model.ProductResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.base.path}/dropbox")
public class DropBoxFasada {

    private final DropBoxMediator dropBoxMediator;

    @RequestMapping(method = RequestMethod.POST, value = "/add",consumes = "multipart/form-data")
    public ResponseEntity<DropBoxFile> addFile(
            @RequestParam("file") MultipartFile file
    ){
        DropBoxFile dropBoxFile = dropBoxMediator.addFile(file);
       if (dropBoxFile != null){
           return ResponseEntity.ok(dropBoxFile);
       }
       return ResponseEntity.status(HttpStatusCode.valueOf(404)).build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/get")
    public ResponseEntity<byte[]> getFile(
            @RequestParam String id
    ){
        ByteArrayOutputStream file = null;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        try {
            file = dropBoxMediator.getFile(id);
            return new ResponseEntity<>(file.toByteArray(), headers, HttpStatus.OK);
        } catch (IOException | DbxException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

}
