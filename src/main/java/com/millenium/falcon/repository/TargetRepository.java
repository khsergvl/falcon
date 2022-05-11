package com.millenium.falcon.repository;

import com.millenium.falcon.model.Target;
import org.springframework.data.couchbase.repository.ReactiveCouchbaseRepository;

public interface TargetRepository extends ReactiveCouchbaseRepository<Target, String> {
}
