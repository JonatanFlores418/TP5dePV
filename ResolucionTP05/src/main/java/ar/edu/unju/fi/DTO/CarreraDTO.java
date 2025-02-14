package ar.edu.unju.fi.DTO;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import ar.edu.unju.fi.model.Alumno;
import ar.edu.unju.fi.model.Materia;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Component
public class CarreraDTO {
	
	@NotBlank(message = "El código no puede estar vacío")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "El código solo puede contener letras y números")
    private String codigo;
    
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres")
    private String nombre;
    
    @NotNull(message = "La duración no puede ser nula")
    @Min(value = 1, message = "La duración debe ser al menos 1")
    @Max(value = 10, message = "La duración no puede ser mayor a 10")
    private Integer duracion;
    
    @NotNull(message = "El estado no puede ser nulo")
    private Boolean estado;
	
    @OneToMany(mappedBy = "carrera")
	private List<Alumno> alumnos = new ArrayList<Alumno>();
	
	@OneToMany(mappedBy = "carrera")
	private List<Materia> materias = new ArrayList<Materia>();
}