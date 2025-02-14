package ar.edu.unju.fi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.unju.fi.DTO.AlumnoDTO;


@Service
public interface AlumnoService {
	
	public void guardarAlumno(AlumnoDTO alumnoDTO);
	public List<AlumnoDTO> mostrarAlumno();
	public List<AlumnoDTO> mostrarAlumnoInactivos();
	public void borrarAlumno(String codigo);
	public void borrarDefinitivoAlumno(String codigo);
	public void DardeAlta(String codigo);
	public void modificarAlumno(AlumnoDTO alumnoDTO);
	public AlumnoDTO buscarAlumno(String codigo);
	public void darDeBajaMateria(String LU, String codigoMateria);
    public void darDeBajaCarrera(String LU, String codigoCarrera);
    public void darDeAltaMateria(String LU, String codigoMateria);
    public void darDeAltaCarrera(String LU, String codigoCarrera);
}
