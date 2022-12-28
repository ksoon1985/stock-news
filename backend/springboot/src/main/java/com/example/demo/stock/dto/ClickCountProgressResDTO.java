package com.example.demo.stock.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class ClickCountProgressResDTO {
    private ArrayList<String> dateList;
    private ArrayList<Integer> countList;
}
