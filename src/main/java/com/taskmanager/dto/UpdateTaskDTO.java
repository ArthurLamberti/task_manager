package com.taskmanager.dto;


import com.taskmanager.enums.TaskStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UpdateTaskDTO {

    private String name;
    private TaskStatusEnum status;
    private LocalDateTime updatedAt = LocalDateTime.now();
    private Boolean active;


}
