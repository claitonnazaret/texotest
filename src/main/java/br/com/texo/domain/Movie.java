package br.com.texo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Movie extends SuperEntity implements Serializable {

    @Temporal(TemporalType.DATE)
    private Date year;

    private String title;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Studio> studios = new HashSet<>(0);
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Producer> producers = new HashSet<>(0);
    private boolean winner;
}
