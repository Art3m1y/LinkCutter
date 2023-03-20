package ru.Art3m1y.LinkCutter.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "links")
@Getter
@Setter
@NoArgsConstructor
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String fullLink;
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiredAt;
}
