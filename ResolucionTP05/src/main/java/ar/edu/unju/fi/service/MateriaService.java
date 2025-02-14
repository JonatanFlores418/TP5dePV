package ar.edu.unju.fi.service;
import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.unju.fi.DTO.MateriaDTO;
import ar.edu.unju.fi.model.Materia;

@Service
public interface MateriaService {
	public void guardarMateria(MateriaDTO materiaParaGuardar);
	public List<Materia> mostrarMaterias();
	public List<Materia> mostrarMateriasInactivas();
	public void borrarMateria (String codigo);
	public void borrarDefinitivoMateria (String codigo);
	public void darDeAlta (String codigo);
	public void darDeBajaAlumno (String codigo,String LU);
	public void modificarMateria (MateriaDTO materia);
	public MateriaDTO buscarMateria(String codigo);
}
