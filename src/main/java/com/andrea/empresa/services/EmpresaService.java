package com.andrea.empresa.services;

import com.andrea.empresa.models.Departamento;
import com.andrea.empresa.models.Empleado;
import com.andrea.empresa.repositories.DepartamentoRepository;
import com.andrea.empresa.repositories.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmpresaService {

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    // ══════════════════════════════════════════════════════════
    //  DEPARTAMENTOS
    // ══════════════════════════════════════════════════════════

    public List<Departamento> listarDepartamentos(String nombre, String planta) {
        if (nombre != null && !nombre.isBlank()) {
            return departamentoRepository.findByNombreContainingIgnoreCase(nombre);
        }
        if (planta != null && !planta.isBlank()) {
            return departamentoRepository.findByPlantaContainingIgnoreCase(planta);
        }
        return departamentoRepository.findAll();
    }

    public Optional<Departamento> obtenerDepartamentoPorId(Long id) {
        return departamentoRepository.findById(id);
    }

    public Departamento guardarDepartamento(Departamento departamento) {
        return departamentoRepository.save(departamento);
    }

    public Departamento actualizarDepartamento(Long id, Departamento datos) {
        Departamento dep = departamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Departamento no encontrado con id: " + id));
        dep.setNombre(datos.getNombre());
        dep.setPlanta(datos.getPlanta());
        dep.setPresupuesto(datos.getPresupuesto());
        return departamentoRepository.save(dep);
    }

    public void eliminarDepartamento(Long id) {
        if (!departamentoRepository.existsById(id)) {
            throw new RuntimeException("Departamento no encontrado con id: " + id);
        }
        departamentoRepository.deleteById(id); // cascade elimina empleados
    }

    public Double calcularSalarioTotal(Long depId) {
        Double total = empleadoRepository.calcularSalarioTotalPorDepartamento(depId);
        return total != null ? total : 0.0;
    }

    // ══════════════════════════════════════════════════════════
    //  EMPLEADOS
    // ══════════════════════════════════════════════════════════

    public List<Empleado> listarEmpleados(String puesto, Double salarioMin, Double salarioMax) {
        if (puesto != null || salarioMin != null || salarioMax != null) {
            return empleadoRepository.findByFiltros(puesto, salarioMin, salarioMax);
        }
        return empleadoRepository.findAll();
    }

    public List<Empleado> listarEmpleadosPorDepartamento(Long depId) {
        return empleadoRepository.findByDepartamentoId(depId);
    }

    public Optional<Empleado> obtenerEmpleadoPorId(Long id) {
        return empleadoRepository.findById(id);
    }

    public Empleado guardarEmpleado(Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    public Empleado actualizarEmpleado(Long id, Empleado datos) {
        Empleado emp = empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con id: " + id));
        emp.setNombre(datos.getNombre());
        emp.setPuesto(datos.getPuesto());
        emp.setSalario(datos.getSalario());
        emp.setFechaContratacion(datos.getFechaContratacion());
        if (datos.getDepartamento() != null && datos.getDepartamento().getId() != null) {
            Departamento dep = departamentoRepository.findById(datos.getDepartamento().getId())
                    .orElseThrow(() -> new RuntimeException("Departamento no encontrado"));
            emp.setDepartamento(dep);
        }
        return empleadoRepository.save(emp);
    }

    public void eliminarEmpleado(Long id) {
        if (!empleadoRepository.existsById(id)) {
            throw new RuntimeException("Empleado no encontrado con id: " + id);
        }
        empleadoRepository.deleteById(id);
    }

    /**
     * Mover un empleado a otro departamento (cambiar FK)
     */
    public Empleado moverEmpleado(Long empleadoId, Long nuevoDepartamentoId) {
        Empleado emp = empleadoRepository.findById(empleadoId)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con id: " + empleadoId));
        Departamento nuevoDep = departamentoRepository.findById(nuevoDepartamentoId)
                .orElseThrow(() -> new RuntimeException("Departamento no encontrado con id: " + nuevoDepartamentoId));
        emp.setDepartamento(nuevoDep);
        return empleadoRepository.save(emp);
    }

    public List<Departamento> listarTodosDepartamentos() {
        return departamentoRepository.findAll();
    }
}
