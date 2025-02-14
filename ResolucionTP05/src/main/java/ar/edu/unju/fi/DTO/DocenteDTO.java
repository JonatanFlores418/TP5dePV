package ar.edu.unju.fi.DTO;

import org.springframework.stereotype.Component;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Component
public class DocenteDTO {

    @NotBlank(message = "El legajo no puede estar vacío")
    private String legajoDTO;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 2, message = "El nombre debe tener al menos 2 caracteres")
    private String nombreDTO;

    @NotBlank(message = "El apellido no puede estar vacío")
    @Size(min = 2, message = "El apellido debe tener al menos 2 caracteres")
    private String apellidoDTO;

    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El email debe ser válido")
    private String emailDTO;

    @NotBlank(message = "El teléfono no puede estar vacío")
    @Pattern(regexp = "\\d{10,15}", message = "El teléfono debe tener entre 10 y 15 dígitos")
    private String telefonoDTO;

    private Boolean estadoDTO;
}
