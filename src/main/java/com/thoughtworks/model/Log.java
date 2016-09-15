package com.thoughtworks.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "log")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Log implements Serializable {
    @Id
    private int candidateId;
    private LocalDateTime loginTime;
}
