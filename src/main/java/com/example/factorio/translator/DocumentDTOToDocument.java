package com.example.factorio.translator;


import com.example.factorio.model.Document;
import com.example.factorio.model.DocumentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

@Mapper
public abstract class DocumentDTOToDocument {

    public Document toDocument(DocumentDTO documentDTO){
        return mapToDocument(documentDTO);
    }


    @Mappings({})
    protected abstract Document mapToDocument(DocumentDTO documentDTO);



}
