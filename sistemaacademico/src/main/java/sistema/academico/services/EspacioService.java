package sistema.academico.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sistema.academico.DTO.*;
import sistema.academico.entities.Espacio;
import sistema.academico.repository.EspacioRepository;

@Service
public class EspacioService {

    @Autowired
    private EspacioRepository espacioRepository;

    public EspacioResponseDTO crearEspacio(EspacioRequestDTO espacioRequestDTO) {
        // Convertir DTO a entidad
        Espacio espacio = new Espacio();
        espacio.setNombre(espacioRequestDTO.getNombre());
        espacio.setTipo(espacioRequestDTO.getTipo());
        espacio.setCapacidad(espacioRequestDTO.getCapacidad());
        espacio.setUbicacion(espacioRequestDTO.getUbicacion());
        espacio.setDisponible(espacioRequestDTO.getDisponible());

        // Guardar el espacio en la base de datos
        Espacio espacioGuardado = espacioRepository.save(espacio);

        // Convertir entidad a DTO para la respuesta
        EspacioResponseDTO espacioResponseDTO = new EspacioResponseDTO();
        espacioResponseDTO.setId(espacioGuardado.getId());
        espacioResponseDTO.setNombre(espacioGuardado.getNombre());
        espacioResponseDTO.setTipo(espacioGuardado.getTipo());
        espacioResponseDTO.setCapacidad(espacioGuardado.getCapacidad());
        espacioResponseDTO.setUbicacion(espacioGuardado.getUbicacion());
        espacioResponseDTO.setDisponible(espacioGuardado.getDisponible());

        return espacioResponseDTO;
    }
}
