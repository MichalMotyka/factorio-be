package com.example.factorio.translator;

import com.example.factorio.model.Document;
import com.example.factorio.model.DocumentDTO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public abstract class DocumentToDocumentDTO {

    public DocumentDTO toDocumentDTO(Document document){
        return mapToDocumentDTO(document);
    }

    @Mappings({
            @Mapping(target = "user", ignore = true),
            @Mapping(target = "relatedDocument", ignore = true)
    })
    protected abstract DocumentDTO mapToDocumentDTO(Document document);
}
