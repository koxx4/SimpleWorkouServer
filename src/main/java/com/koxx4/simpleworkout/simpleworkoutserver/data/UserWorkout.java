package com.koxx4.simpleworkout.simpleworkoutserver.data;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "users_workouts")
public class UserWorkout {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    //TODO: OneToOne Trail relation

    @Column(name = "type")
    WorkoutType workoutType;

    @Column(name = "date")
    Date date;

    @Column(name = "note")
    String note;

    @Column(name = "distance")
    Double distance;

}
