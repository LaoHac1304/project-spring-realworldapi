package com.codevui.realworldapp.model.user.dto;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class UserDTOUpdate {

    private String username;
    private String email;
    private String password;
    private String image;
    private String bio;
}
