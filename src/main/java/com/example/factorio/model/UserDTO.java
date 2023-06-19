package com.example.factorio.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private long id;
    private String name;
    private String email;

    public UserDTO(long id) {
        this.id = id;
    }
}
