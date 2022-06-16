package com.millenium.falcon.repository;

import com.millenium.falcon.model.Target;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TargetRepository extends JpaRepository<Target, String> {
}
