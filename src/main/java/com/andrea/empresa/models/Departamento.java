package com.andrea.empresa.models;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "departamentos")
public class Departamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String planta;

    @Column(nullable = false)
    private Double presupuesto;

    @OneToMany(mappedBy = "departamento", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Empleado> empleados = new ArrayList<>();

    // ── Constructores ──────────────────────────────────────────
    public Departamento() {
    }

    public Departamento(String nombre, String planta, Double presupuesto) {
        this.nombre = nombre;
        this.planta = planta;
        this.presupuesto = presupuesto;
    }

    // ── Getters y Setters ──────────────────────────────────────
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPlanta() {
        return planta;
    }

    public void setPlanta(String planta) {
        this.planta = planta;
    }

    public Double getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(Double presupuesto) {
        this.presupuesto = presupuesto;
    }

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }

    // ── Métodos auxiliares ─────────────────────────────────────
    public Double getSalarioTotal() {
        return empleados.stream()
                .mapToDouble(Empleado::getSalario)
                .sum();
    }

    public int getNumeroEmpleados() {
        return empleados.size();
    }
}
