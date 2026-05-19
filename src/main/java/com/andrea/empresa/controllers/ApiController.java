package com.andrea.empresa.controllers;

import com.andrea.empresa.models.Departamento;
import com.andrea.empresa.models.Empleado;
import com.andrea.empresa.services.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private EmpresaService empresaService;

    // ══════════════════════════════════════════════════════════
    // API REST - DEPARTAMENTOS
    // ══════════════════════════════════════════════════════════

    /** GET /api/departamentos?nombre=&planta= */
    @GetMapping("/departamentos")
    public ResponseEntity<List<Departamento>> listarDepartamentos(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String planta) {
        return ResponseEntity.ok(empresaService.listarDepartamentos(nombre, planta));
    }

    /** GET /api/departamentos/{id} */
    @GetMapping("/departamentos/{id}")
    public ResponseEntity<Departamento> obtenerDepartamento(@PathVariable Long id) {
        return empresaService.obtenerDepartamentoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /** POST /api/departamentos */
    @PostMapping("/departamentos")
    public ResponseEntity<Departamento> crearDepartamento(@RequestBody Departamento departamento) {
        Departamento creado = empresaService.guardarDepartamento(departamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    /** PUT /api/departamentos/{id} */
    @PutMapping("/departamentos/{id}")
    public ResponseEntity<Departamento> actualizarDepartamento(@PathVariable Long id,
            @RequestBody Departamento departamento) {
        try {
            return ResponseEntity.ok(empresaService.actualizarDepartamento(id, departamento));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /** DELETE /api/departamentos/{id} */
    @DeleteMapping("/departamentos/{id}")
    public ResponseEntity<Map<String, String>> eliminarDepartamento(@PathVariable Long id) {
        try {
            empresaService.eliminarDepartamento(id);
            return ResponseEntity.ok(Map.of("mensaje", "Departamento eliminado correctamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /** GET /api/departamentos/{id}/salario-total */
    @GetMapping("/departamentos/{id}/salario-total")
    public ResponseEntity<Map<String, Double>> salarioTotal(@PathVariable Long id) {
        return empresaService.obtenerDepartamentoPorId(id)
                .map(d -> ResponseEntity.ok(Map.of("salarioTotal", empresaService.calcularSalarioTotal(id))))
                .orElse(ResponseEntity.notFound().build());
    }

    // ══════════════════════════════════════════════════════════
    // API REST - EMPLEADOS
    // ══════════════════════════════════════════════════════════

    /** GET /api/empleados?puesto=&salarioMin=&salarioMax= */
    @GetMapping("/empleados")
    public ResponseEntity<List<Empleado>> listarEmpleados(
            @RequestParam(required = false) String puesto,
            @RequestParam(required = false) Double salarioMin,
            @RequestParam(required = false) Double salarioMax) {
        return ResponseEntity.ok(empresaService.listarEmpleados(puesto, salarioMin, salarioMax));
    }

    /** GET /api/empleados/{id} */
    @GetMapping("/empleados/{id}")
    public ResponseEntity<Empleado> obtenerEmpleado(@PathVariable Long id) {
        return empresaService.obtenerEmpleadoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /** POST /api/empleados */
    @PostMapping("/empleados")
    public ResponseEntity<Empleado> crearEmpleado(@RequestBody Empleado empleado) {
        Empleado creado = empresaService.guardarEmpleado(empleado);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    /** PUT /api/empleados/{id} */
    @PutMapping("/empleados/{id}")
    public ResponseEntity<Empleado> actualizarEmpleado(@PathVariable Long id,
            @RequestBody Empleado empleado) {
        try {
            return ResponseEntity.ok(empresaService.actualizarEmpleado(id, empleado));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /** DELETE /api/empleados/{id} */
    @DeleteMapping("/empleados/{id}")
    public ResponseEntity<Map<String, String>> eliminarEmpleado(@PathVariable Long id) {
        try {
            empresaService.eliminarEmpleado(id);
            return ResponseEntity.ok(Map.of("mensaje", "Empleado eliminado correctamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * PATCH /api/empleados/{id}/mover?departamentoId=
     * Mover un empleado a otro departamento (cambiar FK)
     */
    @PatchMapping("/empleados/{id}/mover")
    public ResponseEntity<Empleado> moverEmpleado(@PathVariable Long id,
            @RequestParam Long departamentoId) {
        try {
            return ResponseEntity.ok(empresaService.moverEmpleado(id, departamentoId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /** GET /api/departamentos/{id}/empleados */
    @GetMapping("/departamentos/{id}/empleados")
    public ResponseEntity<List<Empleado>> empleadosPorDepartamento(@PathVariable Long id) {
        return empresaService.obtenerDepartamentoPorId(id)
                .map(d -> ResponseEntity.ok(empresaService.listarEmpleadosPorDepartamento(id)))
                .orElse(ResponseEntity.notFound().build());
    }
}
