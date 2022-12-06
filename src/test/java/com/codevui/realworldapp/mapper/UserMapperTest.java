package com.codevui.realworldapp.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.codevui.realworldapp.entity.User;
import com.codevui.realworldapp.model.user.dto.UserDTOCreateRequest;
import com.codevui.realworldapp.model.user.dto.UserDTOReponse;

public class UserMapperTest {
    @Test
    void testToUserDTOResponse() {

        //given
        User user = User.builder()
        .email("quan@gmail.com")
        .username("quan")
        .bio("bio")
        .image("image")
        .build();

        UserDTOReponse expect = UserDTOReponse.builder()
        .email("quan@gmail.com")
        .username("quan")
        .bio("bio")
        .image("image")
        .build();

        //when
        UserDTOReponse actual = UserMapper.toUserDTOResponse(user);

        //then
        assertEquals(expect, actual);

    }

    @Test
    void testToUser() {

        //given
        UserDTOCreateRequest userDTOCreateRequest = UserDTOCreateRequest.builder()
        .username("username")
        .email("email")
        .password("password")
        .build();

        User expected = User.builder()
        .username("username")
        .email("email")
        .password("password")
        .build();

        //when
        User actual = UserMapper.toUser(userDTOCreateRequest);


        //then
        assertEquals(expected.getUsername(), actual.getUsername());

    }
}
