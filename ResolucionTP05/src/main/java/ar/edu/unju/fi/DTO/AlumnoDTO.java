package ar.edu.unju.fi.DTO;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import ar.edu.unju.fi.model.Carrera;
import ar.edu.unju.fi.model.Materia;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@Component
public class AlumnoDTO {
	
	@Id
	@Column(unique = true)
    private String LU;
	
	@Pattern(regexp = "\\d+", message = "El DNI debe contener solo números")
	private String dni;
	
	@Size(min = 3, max = 20, message = "El nombre debe contener como mínimo 3 caracteres y como máximo 20 caracteres")
    private String nombre;
	
	@Size(min = 3, max = 20, message = "El apellido debe contener como mínimo 3 caracteres y como máximo 20 caracteres")
    private String apellido;
    
	@NotBlank(message = "Debe ingresar su correo")
    @Email(message = "Debe ingresar un correo válido")
    private String email;
	
	@NotBlank(message = "Debe ingresar su teléfono")
    @Pattern(regexp = "\\d+", message = "El teléfono debe contener solo números")
    private String telefono;
	
    @Past(message = "La fecha de nacimiento debe ser anterior a la fecha actual")
    private Date fechaNacimiento;
	
	@NotBlank(message = "Debe ingresar su domicilio")
    @Size(min = 3, max = 20, message = "El domicilio debe contener como mínimo 3 caracteres y como máximo 20 caracteres")
    private String domicilio;
	
	@NotNull(message = "Debe ingresar el estado")
    private Boolean estado;
	
	@ManyToOne
    @JoinColumn(name = "codigo_carrera")
	private Carrera carrera;
	
	@ManyToMany
	private List<Materia> Materias = new ArrayList<Materia>();
}
