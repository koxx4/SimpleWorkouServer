package com.koxx4.simpleworkout.simpleworkoutserver.data;

import javax.persistence.*;

@Entity
@Table(name = "trails")
public class Trail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    Long id;

}
