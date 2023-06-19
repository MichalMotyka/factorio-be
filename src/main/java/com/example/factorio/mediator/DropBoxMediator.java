package com.example.factorio.mediator;

import com.dropbox.core.DbxException;
import com.example.factorio.model.DropBoxFile;
import com.example.factorio.model.ProductDTO;
import com.example.factorio.service.DropBoxService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Component
@RequiredArgsConstructor
public class DropBoxMediator {


    private final DropBoxService dropBoxService;

    public DropBoxFile addFile(MultipartFile file) {
        return dropBoxService.addFile(file);
    }

    public ByteArrayOutputStream getFile(String id) throws IOException, DbxException {
       return dropBoxService.getFile(id);
    }
}
