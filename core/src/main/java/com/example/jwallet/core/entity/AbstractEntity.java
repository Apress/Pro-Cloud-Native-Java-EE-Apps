package com.example.jwallet.core.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@MappedSuperclass
@EntityListeners({AbstractEntityEntityListener.class})
@Getter
@Setter
public abstract class AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    protected LocalDateTime created;
    protected LocalDateTime updated;

    protected String createdBy;
    protected String editedBy;


}
