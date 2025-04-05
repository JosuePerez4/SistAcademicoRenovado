package sistema.academico.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sistema.academico.entities.Reporte;
import sistema.academico.entities.Usuario;
import sistema.academico.repository.ReporteRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReporteService {

    @Autowired
    private ReporteRepository reporteRepository;

    // Crear un nuevo reporte
    public Reporte crearReporte(String titulo, String tipo, String descripcion, Usuario autor) {
        Reporte reporte = new Reporte();
        reporte.setTitulo(titulo);
        reporte.setTipo(tipo);
        reporte.setDescripcion(descripcion);
        reporte.setAutor(autor);
        reporte.setFechaGeneracion(new Date()); // Fecha actual
        return reporteRepository.save(reporte);
    }

    // Obtener todos los reportes
    public List<Reporte> obtenerTodosLosReportes() {
        return reporteRepository.findAll();
    }

    // Buscar reportes por título
    public List<Reporte> buscarPorTitulo(String titulo) {
        return reporteRepository.findByTitulo(titulo);
    }

    // Buscar reportes por tipo
    public List<Reporte> buscarPorTipo(String tipo) {
        return reporteRepository.findByTipo(tipo);
    }

    // Buscar reportes por autor
    public List<Reporte> buscarPorAutor(Usuario autor) {
        return reporteRepository.findByAutor(autor);
    }

    // Buscar reportes por rango de fechas
    public List<Reporte> buscarPorRangoDeFechas(Date inicio, Date fin) {
        return reporteRepository.findByFechaGeneracionBetween(inicio, fin);
    }

    // Modificar la descripción de un reporte
    public boolean modificarDescripcion(Long idReporte, String nuevaDescripcion) {
        Optional<Reporte> reporteOpt = reporteRepository.findById(idReporte);
        if (reporteOpt.isPresent()) {
            Reporte reporte = reporteOpt.get();
            reporte.setDescripcion(nuevaDescripcion);
            reporteRepository.save(reporte);
            return true;
        }
        return false; // Si no se encuentra el reporte
    }

    // Eliminar un reporte por ID
    public boolean eliminarReporte(Long idReporte) {
        Optional<Reporte> reporteOpt = reporteRepository.findById(idReporte);
        if (reporteOpt.isPresent()) {
            reporteRepository.deleteById(idReporte);
            return true;
        }
        return false; // Si el reporte no existe
    }

    // Descargar un reporte (simulación de descarga)
    public String descargarReporte(Long idReporte) {
        Optional<Reporte> reporteOpt = reporteRepository.findById(idReporte);
        if (reporteOpt.isPresent()) {
            Reporte reporte = reporteOpt.get();
            // Aquí puedes agregar lógica para generar un archivo descargable (PDF, etc.)
            return "Reporte descargado: " + reporte.getTitulo();
        }
        return "Reporte no encontrado.";
    }
}
