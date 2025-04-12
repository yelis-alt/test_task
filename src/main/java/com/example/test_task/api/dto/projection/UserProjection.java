package com.example.test_task.api.dto.projection;

import java.time.LocalDate;

public interface UserProjection {
    Long getId();
    String getName();
    LocalDate getDateOfBirth();
}
