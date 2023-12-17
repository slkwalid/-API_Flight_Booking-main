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
    private Long id; //Plane's id

    @NotBlank(message = "Operator can't be null")
    @Column(nullable = false)
    private String operator; //Plane's operator

    @NotBlank(message = "Model can't be null")
    @Column(nullable = false)
    private String model; //Plane's model

    @NotBlank(message = "Registration can't be null")
    @Size(max = 6, message = "Registration has a maximum of 6 characters")
    @Column(nullable = false, unique = true)
    private String registration; //Plane's registration

    @NotNull(message = "Capacity can't be null")
    @Column(nullable = false)
    @Min(value = 6, message = "Capacity is a maximum of 6")
    private Integer capacity; //Plane's capacity
}