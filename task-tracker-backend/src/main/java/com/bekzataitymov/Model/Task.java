package com.bekzataitymov.Model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "header")
    @NonNull
    private String header;

    @Column(name = "description")
    @NonNull
    private String description;

    @ManyToOne(fetch = FetchType.EAGER, optional=false)
    @JoinColumn(name = "author_id", nullable = false)
    @NonNull
    private User user;

    @Column(name = "\"isCompleted\"")
    @NonNull
    private boolean isCompleted;

    @Column(name = "timestamp")
    private Timestamp timestamp;
}
