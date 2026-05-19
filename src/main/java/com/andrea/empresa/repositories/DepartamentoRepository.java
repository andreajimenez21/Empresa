package com.andrea.empresa.repositories;

import com.andrea.empresa.models.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {

    // Filtrar por nombre (query param opcional)
    List<Departamento> findByNombreContainingIgnoreCase(String nombre);

    // Filtrar por planta
    List<Departamento> findByPlantaContainingIgnoreCase(String planta);

    // Departamentos con más de N empleados
    @Query("SELECT d FROM Departamento d WHERE SIZE(d.empleados) > :n")
    List<Departamento> findDepartamentosConMasDeNEmpleados(int n);
}
