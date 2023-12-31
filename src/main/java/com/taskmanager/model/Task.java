package com.taskmanager.model;

import com.taskmanager.enums.TaskStatusEnum;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.time.LocalDateTime;

@Entity(name = "tasks")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Task {

    @Id
    @GenericGenerator(
            name = "custom_sequence",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "customers_sequence"),
                    @Parameter(name = "initial_value", value = "1000"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    @GeneratedValue(generator = "custom_sequence")
    @Column(name = "task_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "estimated_date")
    private LocalDateTime estimatedDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "status", nullable = false)
    private TaskStatusEnum status;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false, updatable = false)
    private User user;

    @Column(name = "active", nullable = false, updatable = false)
    private boolean active;

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("id: ").append(this.id).append(";");
        sb.append("name: ").append(this.name).append(";");
        sb.append("status: ").append(this.status).append(";");
        sb.append("active: ").append(this.active).append(";");
        sb.append("user: ").append(this.user).append(";");
        return sb.toString();
    }
}
