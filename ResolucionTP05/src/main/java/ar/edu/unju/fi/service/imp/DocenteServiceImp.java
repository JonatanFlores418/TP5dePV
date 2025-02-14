 package ar.edu.unju.fi.service.imp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ar.edu.unju.fi.DTO.DocenteDTO;
import ar.edu.unju.fi.map.DocenteMapDTO;
import ar.edu.unju.fi.model.Docente;
import ar.edu.unju.fi.repository.DocenteRepository;
import ar.edu.unju.fi.service.DocenteService;

@Service
public class DocenteServiceImp implements DocenteService {
	private static final Logger logger = LoggerFactory.getLogger(DocenteServiceImp.class);

	@Autowired 
	DocenteMapDTO docenteMapDTO;
	
	@Autowired
	DocenteRepository docenteRepository;
	
	
	@Override
	public List<DocenteDTO> MostrarDocente() {
        logger.info("Mostrando docentes");
        return docenteMapDTO.toDocenteDTOList(docenteRepository.findDocenteByEstado(true));
    }

	@Override
	 public void save(DocenteDTO docenteDTO) {
        logger.info("Guardando docente");
        if (docenteDTO == null) {
            logger.error("DocenteDTO vacio");
        }
        docenteDTO.setEstadoDTO(true);
        docenteRepository.save(docenteMapDTO.toDocente(docenteDTO));
    }

	@Override
	public void deleteByLegajo(String legajo) {
        logger.info("Borrando docente con legajo {}", legajo); 
        if (legajo == null || legajo.isEmpty()) {
            logger.error("deleteByLegajo() - Legajo es nulo o vacío");
           
        }
        List<Docente> todosLosDocentes = docenteRepository.findAll();
        for (Docente docente : todosLosDocentes) {
            if (docente.getLegajo().equals(legajo)) {
                docente.setEstado(false);
                docenteRepository.save(docente);
                break;
            }
        }
	}
	
	@Override
	public void edit(DocenteDTO docenteDTO) {
        logger.info("Editando Docente con legajo: {}", docenteDTO.getLegajoDTO());
        DocenteDTO docenteExistente = this.buscaDocente(docenteDTO.getLegajoDTO());    
        if (docenteExistente != null) {
            docenteExistente.setApellidoDTO(docenteDTO.getApellidoDTO());
            docenteExistente.setEmailDTO(docenteDTO.getEmailDTO());
            docenteExistente.setEstadoDTO(docenteDTO.getEstadoDTO());
            docenteExistente.setLegajoDTO(docenteDTO.getLegajoDTO());
            docenteExistente.setNombreDTO(docenteDTO.getNombreDTO());
            docenteExistente.setTelefonoDTO(docenteDTO.getTelefonoDTO());
            docenteRepository.save(docenteMapDTO.toDocente(docenteExistente));
        } else {
            logger.error("Docente no encontrado con legajo: {}", docenteDTO.getLegajoDTO());
        }
      
    }

	@Override
	public DocenteDTO buscaDocente(String legajo) {
        logger.info("Buscando docente con legajo: {}", legajo);
        if (legajo == null || legajo.isEmpty()) {
            logger.error("Legajo es nulo o vacío");
          
        }
        List<Docente> todosLasCarreras = docenteRepository.findAll();
        for (Docente docente : todosLasCarreras) {
            if (docente.getLegajo().equals(legajo)) {
                logger.trace("buscaDocente() - fin");
                return docenteMapDTO.toDocenteDTO(docente);
            }
        }
        return null;
    }

	@Override
	public void darDeAlta(String legajo) {
        logger.info("Dando de alta Docente con legajo: {}", legajo);
        if (legajo == null || legajo.isEmpty()) {
            logger.error("Legajo es nulo o vacío");
        }
        List<Docente> todosLosDocentes = docenteRepository.findAll();
        for (Docente docente : todosLosDocentes) {
            if (docente.getLegajo().equals(legajo)) {
                docente.setEstado(true);
                docenteRepository.save(docente);
                break;
            }
        }
       
    }

	@Override
	public List<DocenteDTO> MostrarDocenteInactivos() {
		// TODO Auto-generated method stub
		logger.info("Mostrando docentes inactivos");
		return docenteMapDTO.toDocenteDTOList(docenteRepository.findDocenteByEstado(false));
	}

	@Override
	public void deletDefinitiveeByLegajo(String legajo) {
		// TODO Auto-generated method stub
		  logger.info("Borrando definitvamente a Docente con legajo: {}", legajo);
	        if (legajo == null || legajo.isEmpty()) {
	            logger.error("Legajo es nulo o vacío");
	            throw new IllegalArgumentException("Legajo no puede ser nulo o vacío");
	        }
	    Docente docente = docenteRepository.findById(legajo).orElse(null);
	    
	    if (docente != null) {
	        docenteRepository.delete(docente);
	    } 
	}




	
	
	
	
	
}