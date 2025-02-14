package ar.edu.unju.fi.map;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import ar.edu.unju.fi.DTO.DocenteDTO;
import ar.edu.unju.fi.model.Docente;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DocenteMapDTO {
	@Mapping(source="legajo", target="legajoDTO")
	@Mapping(source="nombre", target="nombreDTO")
	@Mapping(source="apellido", target="apellidoDTO")
	@Mapping(source="email", target="emailDTO")
	@Mapping(source="telefono", target="telefonoDTO")
	@Mapping(source="estado", target="estadoDTO")
	
	DocenteDTO toDocenteDTO(Docente d); 
	
	@InheritInverseConfiguration
	Docente toDocente(DocenteDTO ddto); 
		
	List<DocenteDTO> toDocenteDTOList (List<Docente> listaD);
	
	List<Docente> ToDocenteList (List<DocenteDTO> listaDDTO);
	
	
}