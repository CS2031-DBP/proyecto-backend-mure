package dbp.proyecto.artist.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    // TODO: Implementar la tabla intermedia con Album

    // TODO: Implementar la tabla intermedia con Song

    String description;

    Date birthDate;

    Boolean verified;


}
