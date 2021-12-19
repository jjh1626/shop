package com.example.shop.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Member {
    private Long memberId;
    private String name;
    private String city;
    private String street;
    private String zipcode;
    private String gender;
    private String hobby;

    public static Member createMember(String name, String city, String street, String zipcode, String gender, String hobby) {
        Member member = new Member();
        member.setName(name);
        member.setCity(city);
        member.setStreet(street);
        member.setZipcode(zipcode);
        member.setGender(gender);
        member.setHobby(hobby);
        return member;
    }
}
