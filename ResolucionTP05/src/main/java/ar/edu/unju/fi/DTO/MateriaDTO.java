package ar.edu.unju.fi.DTO;




import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import ar.edu.unju.fi.model.Alumno;
import ar.edu.unju.fi.model.Carrera;
import ar.edu.unju.fi.model.Docente;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
public class MateriaDTO {
		
	@Id
    @NotBlank(message = "El código no puede estar vacío")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "El código solo puede contener letras y números")
    private String codigo;
    
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 3,max = 100, message = "El nombre no puede tener más de 100 caracteres")
    private String nombre;
    
    @NotBlank(message = "El curso no puede estar vacío")
    @Size(max = 100, message = "El curso no puede tener más de 100 caracteres")
    private String curso;
    
    private int cantidadHoras;
    
    @NotBlank(message = "La modalidad no puede estar vacía")
    @Size(max = 50, message = "La modalidad no puede tener más de 50 caracteres")
    private String modalidad;
    
    @NotNull(message = "El estado no puede ser nulo")
    private Boolean estado;
    
    @ManyToOne
    @JoinColumn(name = "codigo_carrera")
	private Carrera carrera;
    
    @ManyToMany
	private List<Alumno> alumnos = new ArrayList<Alumno>();
    
    @OneToOne
    private Docente docente;
}
