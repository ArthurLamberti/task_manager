package com.taskmanager.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateTaskDTO {

    @NotNull
    private String name;

    @Future
    private LocalDateTime startDate;

    @Future
    private LocalDateTime estimatedDate;

    @Future
    private LocalDateTime endDate;


}
