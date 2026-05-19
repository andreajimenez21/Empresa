package com.andrea.empresa.repositories;

import com.andrea.empresa.models.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    // Listar empleados de un departamento
    List<Empleado> findByDepartamentoId(Long departamentoId);

    // Filtrar por puesto (query param opcional)
    List<Empleado> findByPuestoContainingIgnoreCase(String puesto);

    // Filtrar por rango salarial
    List<Empleado> findBySalarioBetween(Double min, Double max);

    // Filtrar por puesto Y rango salarial
    @Query("SELECT e FROM Empleado e WHERE " +
           "(:puesto IS NULL OR LOWER(e.puesto) LIKE LOWER(CONCAT('%', :puesto, '%'))) AND " +
           "(:salarioMin IS NULL OR e.salario >= :salarioMin) AND " +
           "(:salarioMax IS NULL OR e.salario <= :salarioMax)")
    List<Empleado> findByFiltros(@Param("puesto") String puesto,
                                  @Param("salarioMin") Double salarioMin,
                                  @Param("salarioMax") Double salarioMax);

    // Salario total por departamento
    @Query("SELECT SUM(e.salario) FROM Empleado e WHERE e.departamento.id = :depId")
    Double calcularSalarioTotalPorDepartamento(@Param("depId") Long depId);

    // Empleados sin departamento asignado
    List<Empleado> findByDepartamentoIsNull();
}
