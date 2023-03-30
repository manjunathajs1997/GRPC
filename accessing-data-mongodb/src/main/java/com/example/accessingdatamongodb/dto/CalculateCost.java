package com.example.accessingdatamongodb.dto;


import lombok.Data;

@Data
public class CalculateCost {
    private String year;
    private String state;
    private String type;
    private String length;
    private String expense;

}
