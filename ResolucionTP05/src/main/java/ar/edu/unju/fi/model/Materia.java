package ar.edu.unju.fi.model;

import java.util.ArrayList;

import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Component
@Entity
public class Materia {
	  @Id
    private String codigo;    
    private String nombre;
    private String curso;
    private int cantidadHoras;
    private String modalidad;
    private Boolean estado;
	
    @ManyToOne
    @JoinColumn(name = "codigo_carrera")
    private Carrera carrera;
    
    @ManyToMany
    private List<Alumno> alumnos = new ArrayList<Alumno>();
    
    @OneToOne
    private Docente docente;
}
