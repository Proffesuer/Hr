package com.ogoma.hr_core.boundaries.performance.goals.repository;

import com.ogoma.hr_core.boundaries.performance.goals.entities.GoalTracking;
import com.ogoma.hr_core.boundaries.performance.promotion.repository.BaseRepo;
import org.springframework.stereotype.Repository;

@Repository
public interface GoalTrackingRepository extends BaseRepo<GoalTracking> {
}
