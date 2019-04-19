package main.ElectionManager.repository;

import main.ElectionManager.model.ElectionStatistics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElectionStatisticsRepository extends JpaRepository<ElectionStatistics, Integer> {
}
