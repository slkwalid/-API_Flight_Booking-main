package beans;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalTime;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "flights")
public class Vol extends PanacheEntityBase {
    @Id
    @SequenceGenerator(name = "flights_sequence_in_java_code", sequenceName = "flights_sequence_in_database", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "flights_sequence_in_java_code")
    private Long id; //Flight's id

    @NotBlank(message = "Flight number can't be null")
    @Column(nullable = false, unique = true)
    private String number; //Flight's number

    @NotBlank(message = "Flight's origin can't be null")
    @Column(nullable = false)
    private String origin; //Flight's origin place

    @NotBlank(message = "Flight's destination can't be null")
    @Column(nullable = false)
    private String destination; //Flight's destination

    @NotNull(message = "Flight's departure date can't be null")
    @Column(nullable = false)
    private LocalDate departure_date; //Flight's departure date

    @NotNull(message = "Flight's departure time can't be null")
    @Column(nullable = false)
    private LocalTime departure_time; //Flight's departure time

    @NotNull(message = "Flight arrival date can't be null")
    @Column(nullable = false)
    private LocalDate arrival_date; //Flight's arrival date

    @NotNull(message = "Flight's arrival_time can't be null")
    @Column(nullable = false)
    private LocalTime arrival_time; //Flight's arrival time

    @NotNull(message="Plane's id can't be null")
    @ManyToOne
    @JoinColumn(name = "plane_id", nullable = false)
    private Avion plane_id; //Plane's id
}
