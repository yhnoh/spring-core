package com.example.di.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Hello {
    private String hello = "%s 안녕하세요";

    public String helloFormat(String name){
        return String.format(hello, name);
    }
}
