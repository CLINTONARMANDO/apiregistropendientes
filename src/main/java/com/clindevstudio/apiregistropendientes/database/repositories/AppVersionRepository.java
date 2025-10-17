package com.clindevstudio.apiregistropendientes.database.repositories;


import com.clindevstudio.apiregistropendientes.database.entities.AppVersion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppVersionRepository extends JpaRepository<AppVersion, Long> {
    Page<AppVersion> findByVigenteTrue(Pageable pageable);
    Optional<AppVersion> findTopByOrderByIdDesc();
}