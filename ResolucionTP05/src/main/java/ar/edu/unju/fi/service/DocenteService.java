package ar.edu.unju.fi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.unju.fi.DTO.DocenteDTO;

@Service
public interface DocenteService {
	

	public List<DocenteDTO> MostrarDocente();
	public List<DocenteDTO> MostrarDocenteInactivos();
	public void save(DocenteDTO docenteDTO);
	public void deleteByLegajo(String legajo);
	public void deletDefinitiveeByLegajo(String legajo);
	public void darDeAlta(String legajo);
	public void edit(DocenteDTO docenteDTO);
	public DocenteDTO buscaDocente(String legajo);
	
	
}