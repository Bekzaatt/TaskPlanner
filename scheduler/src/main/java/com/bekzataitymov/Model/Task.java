package com.bekzataitymov.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "header")
    private String header;

    @Column(name = "description")
    private String description;
    @ManyToOne(fetch = FetchType.EAGER, optional=false)
    @JoinColumn(name = "author_id", nullable = false)
    private User user;

    @Column(name = "\"isCompleted\"")
    private boolean isCompleted;

    @Column(name = "timestamp")
    private Timestamp timestamp;
}
