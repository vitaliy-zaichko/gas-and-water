package com.coolcompany.gasandwaterusage.repository;

import com.coolcompany.gasandwaterusage.model.Measurement;
import com.coolcompany.gasandwaterusage.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MeasurementRepository extends JpaRepository<Measurement, String> {
    @Query("select m from Measurement m where m.user.id = :userId order by m.measurementDate")
    List<Measurement> findAllByUserId(@Param("userId") String userId);
}
