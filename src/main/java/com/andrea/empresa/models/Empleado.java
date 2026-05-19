package com.andrea.empresa.models;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.time.LocalDate;

@Entity
@Table(name = "empleados")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String puesto;

    @Column(nullable = false)
    private Double salario;

    @Column(nullable = false)
    private LocalDate fechaContratacion;

    @ManyToOne
    @JoinColumn(name = "departamento_id")
    @JsonBackReference
    private Departamento departamento;

    // ── Constructores ──────────────────────────────────────────
    public Empleado() {
    }

    public Empleado(String nombre, String puesto, Double salario, LocalDate fechaContratacion,
            Departamento departamento) {
        this.nombre = nombre;
        this.puesto = puesto;
        this.salario = salario;
        this.fechaContratacion = fechaContratacion;
        this.departamento = departamento;
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

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public LocalDate getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(LocalDate fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }
}
