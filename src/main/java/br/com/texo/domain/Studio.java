package br.com.texo.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Studio extends SuperEntity implements Serializable {
    private String name;

    public Studio(String name) {
        this.name = name;
    }
}
