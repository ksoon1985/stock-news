package com.example.demo.member.model;

public enum Role {
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_MEMBER("ROLE_MEMBER");

    private final String roleName;

    Role(String roleName){
        this.roleName = roleName;
    }

    public String getRoleName(){
        return roleName;
    }
}
