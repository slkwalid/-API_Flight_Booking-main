package beans;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "planes")
public class Avion extends PanacheEntityBase {

    @Id
    @SequenceGenerator(name = "planes_sequence_in_java_code", sequenceName = "planes_sequence_in_database", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "planes_sequence_in_java_code")
    private Long id;

    @NotBlank(message = "La compagnie ne peut pas être nulle !")
    @Column(nullable = false)
    private String operator; //Compagnie de l'avion !

    @NotBlank(message = "Le modèle de l'avion ne peut pas être nul !")
    @Column(nullable = false)
    private String model; //Modèle de l'avion !

    @NotBlank(message = "L'immatriculation de l'avion ne peut pas être nulle !")
    @Size(max = 6, message = "L'immatriculation à un maximum de 6 caractères !")
    @Column(nullable = false, unique = true)
    private String registration; //Immatriculation !

    @NotNull(message = "La capacité ne peut pas être nulle !")
    @Column(nullable = false)
    @Min(value = 6, message = "La capacité doit être au minimum de 6 !")
    private Integer capacity; //Capacité de l'avion !
}