package com.example.jwallet.core.entity;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChangeLog {
    protected LocalDateTime created;
    protected LocalDateTime updated;

    protected String createdBy;
    protected String editedBy;

}
