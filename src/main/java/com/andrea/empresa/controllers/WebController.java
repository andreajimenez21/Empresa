package com.andrea.empresa.controllers;

import com.andrea.empresa.models.Departamento;
import com.andrea.empresa.models.Empleado;
import com.andrea.empresa.services.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/web")
public class WebController {

    @Autowired
    private EmpresaService empresaService;

    // ══════════════════════════════════════════════════════════
    //  WEB - DEPARTAMENTOS
    // ══════════════════════════════════════════════════════════

    /** GET /web/departamentos?nombre=&planta= */
    @GetMapping("/departamentos")
    public String listarDepartamentos(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String planta,
            Model model) {
        List<Departamento> lista = empresaService.listarDepartamentos(nombre, planta);
        model.addAttribute("departamentos", lista);
        model.addAttribute("nombre", nombre);
        model.addAttribute("planta", planta);
        return "departamentos/lista";
    }

    /** GET /web/departamentos/{id} — detalle con lista de empleados */
    @GetMapping("/departamentos/{id}")
    public String detalleDepartamento(@PathVariable Long id, Model model) {
        Departamento dep = empresaService.obtenerDepartamentoPorId(id)
                .orElseThrow(() -> new RuntimeException("Departamento no encontrado"));
        Double salarioTotal = empresaService.calcularSalarioTotal(id);
        model.addAttribute("departamento", dep);
        model.addAttribute("salarioTotal", salarioTotal);
        return "departamentos/detalle";
    }

    /** GET /web/departamentos/nueva */
    @GetMapping("/departamentos/nueva")
    public String formularioNuevoDepartamento(Model model) {
        model.addAttribute("departamento", new Departamento());
        return "departamentos/nueva";
    }

    /** POST /web/departamentos/nueva */
    @PostMapping("/departamentos/nueva")
    public String guardarNuevoDepartamento(@ModelAttribute Departamento departamento) {
        empresaService.guardarDepartamento(departamento);
        return "redirect:/web/departamentos";
    }

    /** GET /web/departamentos/editar/{id} */
    @GetMapping("/departamentos/editar/{id}")
    public String formularioEditarDepartamento(@PathVariable Long id, Model model) {
        Departamento dep = empresaService.obtenerDepartamentoPorId(id)
                .orElseThrow(() -> new RuntimeException("Departamento no encontrado"));
        model.addAttribute("departamento", dep);
        return "departamentos/editar";
    }

    /** POST /web/departamentos/editar/{id} */
    @PostMapping("/departamentos/editar/{id}")
    public String guardarCambiosDepartamento(@PathVariable Long id,
                                              @ModelAttribute Departamento departamento) {
        empresaService.actualizarDepartamento(id, departamento);
        return "redirect:/web/departamentos";
    }

    /** POST /web/departamentos/borrar/{id} */
    @PostMapping("/departamentos/borrar/{id}")
    public String borrarDepartamento(@PathVariable Long id) {
        empresaService.eliminarDepartamento(id);
        return "redirect:/web/departamentos";
    }

    // ══════════════════════════════════════════════════════════
    //  WEB - EMPLEADOS
    // ══════════════════════════════════════════════════════════

    /** GET /web/empleados?puesto=&salarioMin=&salarioMax= */
    @GetMapping("/empleados")
    public String listarEmpleados(
            @RequestParam(required = false) String puesto,
            @RequestParam(required = false) Double salarioMin,
            @RequestParam(required = false) Double salarioMax,
            Model model) {
        List<Empleado> lista = empresaService.listarEmpleados(puesto, salarioMin, salarioMax);
        model.addAttribute("empleados", lista);
        model.addAttribute("puesto", puesto);
        model.addAttribute("salarioMin", salarioMin);
        model.addAttribute("salarioMax", salarioMax);
        return "empleados/lista";
    }

    /** GET /web/empleados/{id} */
    @GetMapping("/empleados/{id}")
    public String detalleEmpleado(@PathVariable Long id, Model model) {
        Empleado emp = empresaService.obtenerEmpleadoPorId(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
        model.addAttribute("empleado", emp);
        return "empleados/detalle";
    }

    /** GET /web/empleados/nueva */
    @GetMapping("/empleados/nueva")
    public String formularioNuevoEmpleado(Model model) {
        model.addAttribute("empleado", new Empleado());
        model.addAttribute("departamentos", empresaService.listarTodosDepartamentos());
        return "empleados/nueva";
    }

    /** POST /web/empleados/nueva */
    @PostMapping("/empleados/nueva")
    public String guardarNuevoEmpleado(@ModelAttribute Empleado empleado,
                                        @RequestParam(required = false) Long departamentoId) {
        if (departamentoId != null) {
            empresaService.obtenerDepartamentoPorId(departamentoId)
                    .ifPresent(empleado::setDepartamento);
        }
        empresaService.guardarEmpleado(empleado);
        return "redirect:/web/empleados";
    }

    /** GET /web/empleados/editar/{id} */
    @GetMapping("/empleados/editar/{id}")
    public String formularioEditarEmpleado(@PathVariable Long id, Model model) {
        Empleado emp = empresaService.obtenerEmpleadoPorId(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
        model.addAttribute("empleado", emp);
        model.addAttribute("departamentos", empresaService.listarTodosDepartamentos());
        return "empleados/editar";
    }

    /** POST /web/empleados/editar/{id} */
    @PostMapping("/empleados/editar/{id}")
    public String guardarCambiosEmpleado(@PathVariable Long id,
                                          @ModelAttribute Empleado empleado,
                                          @RequestParam(required = false) Long departamentoId) {
        if (departamentoId != null) {
            empresaService.obtenerDepartamentoPorId(departamentoId)
                    .ifPresent(empleado::setDepartamento);
        }
        empresaService.actualizarEmpleado(id, empleado);
        return "redirect:/web/empleados";
    }

    /** POST /web/empleados/borrar/{id} */
    @PostMapping("/empleados/borrar/{id}")
    public String borrarEmpleado(@PathVariable Long id) {
        empresaService.eliminarEmpleado(id);
        return "redirect:/web/empleados";
    }

    /** GET /web/empleados/mover/{id} — formulario para cambiar de departamento */
    @GetMapping("/empleados/mover/{id}")
    public String formularioMoverEmpleado(@PathVariable Long id, Model model) {
        Empleado emp = empresaService.obtenerEmpleadoPorId(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
        model.addAttribute("empleado", emp);
        model.addAttribute("departamentos", empresaService.listarTodosDepartamentos());
        return "empleados/mover";
    }

    /** POST /web/empleados/mover/{id} */
    @PostMapping("/empleados/mover/{id}")
    public String moverEmpleado(@PathVariable Long id,
                                 @RequestParam Long nuevoDepartamentoId) {
        empresaService.moverEmpleado(id, nuevoDepartamentoId);
        return "redirect:/web/empleados";
    }

    /** Redirigir raíz a departamentos */
    @GetMapping("/")
    public String inicio() {
        return "redirect:/web/departamentos";
    }
}
