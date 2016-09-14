package com.thoughtworks.model;

import lombok.Getter;

import java.util.Date;

@Getter
public class BootCamp {
    private String name;
    private Date startDate;
    private Date endDate;
    private String company;
}
