package com.ogoma.hr_core.boundaries.access_control.repositories;

import com.ogoma.hr_core.boundaries.access_control.entities.Role;
import com.ogoma.hr_core.boundaries.performance.promotion.repository.BaseRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends BaseRepo<Role> {

    Page<Role> findAllByDeletedFalse( Pageable pageable);
    Page<Role> findAllByDeletedFalse(@Nullable Specification<Role> roleSpecification, Pageable pageable);
}
