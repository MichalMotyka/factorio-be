package com.example.factorio.service;


import com.dropbox.core.DbxDownloader;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.oauth.DbxCredential;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.DownloadBuilder;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.WriteMode;
import com.example.factorio.model.DropBoxFile;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;

@Service
public class DropBoxService {

    @Value("${api.dropbox.token}")
    private String TOKEN;
    private DbxRequestConfig config;
    private DbxClientV2 client;

    @PostConstruct
    private void setConfig(){
        config = DbxRequestConfig.newBuilder("dropbox/FactorioShop").build();
        client = new DbxClientV2(config, getDbxCredential());
    }

    public DropBoxFile addFile(MultipartFile file){
        try (InputStream inputStream = file.getInputStream()) {
            FileMetadata metadata = client.files().uploadBuilder("/" + file.getOriginalFilename())
                    .withMode(WriteMode.ADD)
                    .uploadAndFinish(inputStream);
            return DropBoxFile
                    .builder()
                    .name(metadata.getName())
                    .path(metadata.getPathDisplay())
                    .id(metadata.getId())
                    .build();
        } catch (IOException | DbxException e) {
            e.printStackTrace();
            return null;
        }
    }

    private DbxCredential getDbxCredential() {
        DbxCredential dbxCredential = new DbxCredential(TOKEN);
        return dbxCredential;
    }


    public ByteArrayOutputStream getFile(String name) throws IOException, DbxException {
            DownloadBuilder downloadBuilder = client.files().downloadBuilder("/" + name);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            downloadBuilder.start().download(outputStream);
            return outputStream;
    }
}
