package beans;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "passengers")
public class Passager {
    @Id
    @SequenceGenerator(name = "passengers_sequence_in_java_code", sequenceName = "passengers_sequence_in_database", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "passengers_sequence_in_java_code")
    private Long id;

    @NotBlank(message = "First name can't be null")
    @Column(nullable = false)
    private String surname;

    @NotBlank(message = "Last name can't be null")
    @Column(nullable = false)
    private String firstname;

    @NotBlank(message = "E-mail can't be null")
    @Column(nullable = false)
    private String email_address;
}
