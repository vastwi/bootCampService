package com.thoughtworks.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "candidate")
@EqualsAndHashCode
public class Candidate implements Serializable {
    private String name;
    @Id
    private int id;
    private String company;
}
