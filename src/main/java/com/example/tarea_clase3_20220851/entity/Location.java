package com.example.tarea_clase3_20220851.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "locations")
public class Location {

    @Id
    @Column(name ="location_id")
    private Integer locationId;

    @Column(name = "city")
    private String city;

}