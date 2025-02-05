package food.delivery.minh.modules.schedules.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import food.delivery.minh.common.models.schedules.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer>{
    
}
