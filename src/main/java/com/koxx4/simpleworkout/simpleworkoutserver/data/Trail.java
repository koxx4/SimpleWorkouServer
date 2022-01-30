package com.koxx4.simpleworkout.simpleworkoutserver.data;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "trails")
public class Trail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @OneToMany(mappedBy = "trail", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TrailPoint> trailPoints;

    public Trail() {
    }

    public void addTrailPoint(TrailPoint trailPoint){
        trailPoint.setTrail(this);
        this.trailPoints.add(trailPoint);
    }

    @JsonIgnore
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonManagedReference
    public List<TrailPoint> getTrailPoints() {
        return trailPoints;
    }

    public void setTrailPoints(List<TrailPoint> trailPoints) {
        this.trailPoints = trailPoints;
    }
}
