package com.NWT_KTS_project.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Route {
    private Address start;
    private List<Address> stops;
    private Address destination;
    private Float distance;

}