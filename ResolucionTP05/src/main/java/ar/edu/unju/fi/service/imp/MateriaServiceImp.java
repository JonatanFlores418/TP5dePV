package ar.edu.unju.fi.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unju.fi.DTO.MateriaDTO;
import ar.edu.unju.fi.map.MateriaMapDTO;
import ar.edu.unju.fi.model.Alumno;
import ar.edu.unju.fi.model.Carrera;
import ar.edu.unju.fi.model.Materia;
import ar.edu.unju.fi.repository.AlumnoRepository;
import ar.edu.unju.fi.repository.CarreraRepository;
import ar.edu.unju.fi.repository.MateriaRepository;
import ar.edu.unju.fi.service.MateriaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class MateriaServiceImp implements MateriaService {

    private static final Logger logger = LoggerFactory.getLogger(MateriaServiceImp.class);

    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private MateriaMapDTO materiaMapDTO;

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Autowired
    private CarreraRepository carreraRepository;

    @Override
    public void guardarMateria(MateriaDTO materiaParaGuardar) {
        logger.info("Guardando materia con código {}", materiaParaGuardar.getCodigo());
        materiaParaGuardar.setEstado(true);
        materiaRepository.save(materiaMapDTO.toEntity(materiaParaGuardar));
    }

    @Override
    public List<Materia> mostrarMaterias() {
        logger.info("Mostrando todas las materias activas");
        return materiaRepository.findMateriaByEstado(true);
    }

    @Override
    public void borrarMateria(String codigo) {
        logger.info("Borrando materia con código {}", codigo);
        List<MateriaDTO> todasLasMaterias = materiaMapDTO.listMateriaToListMateriaDTO(materiaRepository.findAll());
        for (MateriaDTO materia : todasLasMaterias) {
            if (materia.getCodigo().equals(codigo)) {
                materia.setEstado(false);
                materia.setDocente(null);
                materiaRepository.save(materiaMapDTO.toEntity(materia));
                break; // Termina el bucle al encontrar la materia
            }
        }
    }

    @Override
    public void modificarMateria(MateriaDTO materia) {
        logger.info("Modificando materia con código {}", materia.getCodigo());
        MateriaDTO materiaExistente = this.buscarMateria(materia.getCodigo());
        if (materiaExistente != null) {
            materiaExistente.setCodigo(materia.getCodigo());
            materiaExistente.setCurso(materia.getCurso());
            materiaExistente.setEstado(materia.getEstado());
            materiaExistente.setModalidad(materia.getModalidad());
            materiaExistente.setCantidadHoras(materia.getCantidadHoras());
            materiaExistente.setNombre(materia.getNombre());
            materiaExistente.setCarrera(materia.getCarrera());
            materiaExistente.setDocente(materia.getDocente());
            materiaRepository.save(materiaMapDTO.toEntity(materiaExistente));
        }
    }

    @Override
    public MateriaDTO buscarMateria(String codigo) {
        logger.info("Buscando materia con código {}", codigo);
        List<MateriaDTO> todasLasMaterias = materiaMapDTO.listMateriaToListMateriaDTO(materiaRepository.findAll());
        for (MateriaDTO materia : todasLasMaterias) {
            if (materia.getCodigo().equals(codigo)) {
                return materia;
            }
        }
        return null;
    }

    @Override
    public void darDeAlta(String codigo) {
        logger.info("Dando de alta materia con código {}", codigo);
        List<MateriaDTO> todasLasMaterias = materiaMapDTO.listMateriaToListMateriaDTO(materiaRepository.findAll());
        for (MateriaDTO materia : todasLasMaterias) {
            if (materia.getCodigo().equals(codigo)) {
                materia.setEstado(true);
                materiaRepository.save(materiaMapDTO.toEntity(materia));
                break; // Termina el bucle al encontrar la materia
            }
        }
    }

    @Override
    public List<Materia> mostrarMateriasInactivas() {
        logger.info("Mostrando todas las materias inactivas");
        return materiaRepository.findMateriaByEstado(false);
    }
    
    @Override
    public void darDeBajaAlumno(String codigo, String LU) {
        logger.info("Dando de baja alumno con LU {} de la carrera con código {}", LU, codigo);
        
        Alumno alumno = alumnoRepository.findById(LU).orElse(null);
        Carrera carrera = carreraRepository.findById(codigo).orElse(null);

        if (alumno == null || carrera == null) {
            logger.error("No se encontró al alumno con LU {} o a la carrera con código {}", LU, codigo);
            return;
        }

        if (alumno.getCarrera() == null || !alumno.getCarrera().equals(carrera)) {
            logger.warn("El alumno con LU {} no está asociado a la carrera con código {}", LU, codigo);
            return;
        }

        alumno.setCarrera(null);
        alumnoRepository.save(alumno);

        carrera.getAlumnos().remove(alumno);
        carreraRepository.save(carrera);

        logger.info("Alumno con LU {} dado de baja correctamente de la carrera con código {}", LU, codigo);
    }

	@Override
	public void borrarDefinitivoMateria(String codigo) {
		// TODO Auto-generated method stub
		Materia materia = materiaRepository.findById(codigo).orElse(null);
	    
	    if (materia != null) {
	        materiaRepository.delete(materia);
	        logger.info("Materia con código {} borrada definitivamente", codigo);
	    }
	}

}
