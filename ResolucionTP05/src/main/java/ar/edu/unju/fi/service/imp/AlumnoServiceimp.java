package ar.edu.unju.fi.service.imp;


import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unju.fi.DTO.AlumnoDTO;
import ar.edu.unju.fi.map.AlumnoMapDTO;
import ar.edu.unju.fi.map.MateriaMapDTO;
import ar.edu.unju.fi.model.Alumno;
import ar.edu.unju.fi.model.Carrera;
import ar.edu.unju.fi.model.Materia;
import ar.edu.unju.fi.repository.AlumnoRepository;
import ar.edu.unju.fi.repository.CarreraRepository;
import ar.edu.unju.fi.repository.MateriaRepository;
import ar.edu.unju.fi.service.AlumnoService;
import ar.edu.unju.fi.service.MateriaService;

@Service
public class AlumnoServiceimp implements AlumnoService{
	
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AlumnoServiceimp.class);
	
	 @Autowired
	MateriaRepository materiaRepository;

	@Autowired
	MateriaMapDTO materiaMapDTO;
	
	@Autowired
	AlumnoMapDTO alumnoMapDTO;
	
	@Autowired
	AlumnoRepository alumnoRepository;
	
	@Autowired
	MateriaService materiaService;
	
	@Autowired
	CarreraRepository carreraRepository;
	
	
	@Override
	public void guardarAlumno(AlumnoDTO alumnoDTO) {
		// TODO Auto-generated method stub
		logger.debug("Guardando alumno: {}", alumnoDTO.getNombre());
		alumnoRepository.save(alumnoMapDTO.convertirAlumnoDTOAAlumno(alumnoDTO));
		logger.info("Alumno guardado exitosamente: {}", alumnoDTO.getNombre());
	}

	@Override
	public List<AlumnoDTO> mostrarAlumno() {
		// TODO Auto-generated method stub
		logger.debug("Consultando lista de alumnos activos");
		List<AlumnoDTO> alumnos = alumnoMapDTO.convertirListaAlumnoAListaAlumnoDTO(alumnoRepository.findAlumnoByEstado(true));
        logger.debug("Se encontraron {} alumnos activos", alumnos.size());
        return alumnos;
	}

	@Override
	public void borrarAlumno(String codigo) {
		// TODO Auto-generated method stub
		logger.debug("Borrando alumno con código: {}", codigo);
		List<Alumno> todosLosAlumnos = alumnoRepository.findAll();
		for (int i = 0 ; i < todosLosAlumnos.size();i++) {
			Alumno alumno = todosLosAlumnos.get(i);
			if(alumno.getLU().equals(codigo)) {
				alumno.setEstado(false);
				alumnoRepository.save(alumno);
				logger.info("Alumno con código {} marcado como inactivo", codigo);
				return;
			}
		}
		logger.warn("No se encontró ningún alumno con código {}", codigo);
	}

	@Override
	public void modificarAlumno(AlumnoDTO alumnoMod) {
		// TODO Auto-generated method stub
		logger.debug("Modificando alumno con código: {}", alumnoMod.getLU());
		alumnoMapDTO.convertirAlumnoDTOAAlumno(alumnoMod);
		AlumnoDTO alumnoExistente = this.buscarAlumno(alumnoMod.getLU());
		if(	alumnoExistente != null){
			alumnoExistente.setNombre(alumnoMod.getNombre());
	        alumnoExistente.setApellido(alumnoMod.getApellido());
	        alumnoExistente.setDni(alumnoMod.getDni());            
	        alumnoRepository.save(alumnoMapDTO.convertirAlumnoDTOAAlumno(alumnoExistente));
	        logger.info("Alumno modificado exitosamente: {}", alumnoMod.getLU());
		}else {
			logger.warn("No se encontró ningún alumno con código {}", alumnoMod.getLU());
		}
		
	}

	@Override
	public AlumnoDTO buscarAlumno(String codigo) {
		// TODO Auto-generated method stub
		logger.debug("Buscando alumno con código: {}", codigo);
		List<Alumno> todosLosAlumnos = alumnoRepository.findAll();
		for (int i = 0 ; i < todosLosAlumnos.size();i++) {
			Alumno alumno = todosLosAlumnos.get(i);
			if(alumno.getLU().equals(codigo)){
				alumno.setFechaNacimiento(todosLosAlumnos.get(i).getFechaNacimiento());
				logger.debug("Alumno encontrado: {}", alumno.getNombre());
				return alumnoMapDTO.convertirAlumnoAAlumnoDTO(alumno);
			}
		}
		logger.warn("No se encontró ningún alumno con código {}", codigo);
		return null;
	}

	@Override
	public void DardeAlta(String codigo) {
		logger.debug("Dando de alta alumno con código: {}", codigo);
		List<Alumno> todosLosAlumnos = alumnoRepository.findAll();
		for (int i = 0 ; i < todosLosAlumnos.size();i++) {
			Alumno alumno = todosLosAlumnos.get(i);
			if(alumno.getLU().equals(codigo)) {
				alumno.setEstado(true);
				alumnoRepository.save(alumno);
				logger.info("Alumno con código {} dado de alta exitosamente", codigo);
				return;
			}
		}
		logger.warn("No se encontró ningún alumno con código {}", codigo);
	}

	@Override
	public List<AlumnoDTO> mostrarAlumnoInactivos() {
		// TODO Auto-generated method stub
		logger.debug("Consultando lista de alumnos inactivos");
        List<AlumnoDTO> alumnosInactivos = alumnoMapDTO.convertirListaAlumnoAListaAlumnoDTO(alumnoRepository.findAlumnoByEstado(false));
        logger.debug("Se encontraron {} alumnos inactivos", alumnosInactivos.size());
        return alumnosInactivos;
	}
	
	@Override
    public void darDeBajaCarrera(String LU, String codigoCarrera) {
        logger.info("Dando de baja carrera {} para el alumno con código: {}", codigoCarrera, LU);
        Alumno alumno = alumnoRepository.findById(LU).orElse(null);
        if (alumno != null) {
        	alumno.getCarrera().getAlumnos().remove(alumno);
            alumno.setCarrera(null);
            alumnoRepository.save(alumno);
            logger.info("Carrera {} dada de baja correctamente para el alumno con código: {}", codigoCarrera, LU);
        } else {
            logger.warn("No se encontró al alumno con código: {}", LU);
        }
    }

	 @Override
	 public void darDeBajaMateria(String LU, String codigoMateria) {
	      logger.info("Dando de baja materia {} para el alumno con código: {}", codigoMateria, LU);
	      Alumno alumno = alumnoRepository.findById(LU).orElse(null);
	      if (alumno != null) {
	          alumno.getMaterias().removeIf(materia -> materia.getCodigo().equals(codigoMateria));
	          alumnoRepository.save(alumno);
	          logger.info("Materia {} dada de baja correctamente para el alumno con código: {}", codigoMateria, LU);
	      } else {
	          logger.warn("No se encontró al alumno con código: {}", LU);
	        }
	}

	 @Override
	 public void darDeAltaMateria(String LU, String codigoMateria) {
	     logger.info("Dando de alta la materia con código {} para el alumno con LU {}", codigoMateria, LU);
	     
	     Alumno alumno = alumnoRepository.findById(LU).orElse(null);
	     Materia materia = materiaRepository.findById(codigoMateria).orElse(null);
	     
	     if (alumno != null && materia != null) {
	         if (!alumno.getMaterias().contains(materia)) {
	             alumno.getMaterias().add(materia);
	             materia.getAlumnos().add(alumno);
	             alumnoRepository.save(alumno);
	             materiaRepository.save(materia);
	             logger.info("Materia con código {} añadida exitosamente al alumno con LU {}", codigoMateria, LU);
	         } else {
	             logger.warn("La materia con código {} ya está presente para el alumno con LU {}", codigoMateria, LU);
	         }
	     } else {
	         logger.error("Error al añadir la materia: Alumno o Materia no encontrados.");
	     }
	 }

	 @Override
	 public void darDeAltaCarrera(String LU, String codigoCarrera) {
	     logger.info("Dando de alta la carrera con código {} para el alumno con LU {}", codigoCarrera, LU);
	     
	     Alumno alumno = alumnoRepository.findById(LU).orElse(null);
	     Carrera carrera = carreraRepository.findById(codigoCarrera).orElse(null);
	     
	     if (alumno != null && carrera != null) {
	         if (alumno.getCarrera() == null) {
	             alumno.setCarrera(carrera);
	             carrera.getAlumnos().add(alumno);
	             
	             // Guardar la carrera y el alumno
	             carreraRepository.save(carrera);
	             alumnoRepository.save(alumno);
	             
	             logger.info("Carrera con código {} añadida exitosamente al alumno con LU {}", codigoCarrera, LU);
	         } else {
	             logger.warn("El alumno con LU {} ya tiene asignada una carrera.", LU);
	         }
	     } else {
	         logger.error("Error al añadir la carrera: Alumno o Carrera no encontrados.");
	     }
	 }

	@Override
	public void borrarDefinitivoAlumno(String codigo) {
		// TODO Auto-generated method stub
		logger.info("Borrando definitivamente alumno con código: {}", codigo);
	    Alumno alumno = alumnoRepository.findById(codigo).orElse(null);
	    
	    if (alumno != null) {
	        alumnoRepository.delete(alumno);
	        logger.info("Alumno con código {} borrado definitivamente", codigo);
	    } else {
	        logger.warn("No se encontró al alumno con código {} para borrar", codigo);
	    }
	}

    
    

}
