package main.ElectionManager.repository;
import main.ElectionManager.model.ElectionChoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ElectionChoiceRepository extends JpaRepository<ElectionChoice, Integer> {
    public List<ElectionChoice> findByElectionId(int electionId);
}
