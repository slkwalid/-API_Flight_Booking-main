package beans;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "reservations")
public class Reservation {
    @Id
    @SequenceGenerator(name = "reservations_sequence_in_java_code", sequenceName = "reservations_sequence_in_database", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reservations_sequence_in_java_code")
    private Long id; //Reservation's id

    @NotNull(message = "Reservation's flight id can't be null")
    @ManyToOne
    @JoinColumn(name = "flight_id",  nullable = false)
    private Vol flight_id; //Reservation's flight id

    @NotNull(message = "Reservation's passenger id can't be null")
    @ManyToOne
    @JoinColumn(name = "passenger_id",  nullable = false)
    private Passager passenger_id; //Reservation's passenger id
}
