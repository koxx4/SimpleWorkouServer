package com.koxx4.simpleworkout.simpleworkoutserver.data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "users_workouts")
public class UserWorkout {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    @JoinColumn(name = "trail_id")
    private Trail trail;

    @Column(name = "type")
    private WorkoutType workoutType;

    @Column(name = "date")
    private Date date;

    @Column(name = "note")
    private String note;

    @Column(name = "distance")
    private Double distance;


    public static enum WorkoutType {
        RUNNING,
        CYCLING,
        HITCH_HIKING,
        WALKING,
        OTHER
    }

    public UserWorkout() {
    }

    public UserWorkout(WorkoutType workoutType, Date date, String note, Double distance) {
        this.workoutType = workoutType;
        this.date = date;
        this.note = note;
        this.distance = distance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Trail getTrail() {
        return trail;
    }

    public void setTrail(Trail trail) {
        this.trail = trail;
    }

    public WorkoutType getWorkoutType() {
        return workoutType;
    }

    public void setWorkoutType(WorkoutType workoutType) {
        this.workoutType = workoutType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}
