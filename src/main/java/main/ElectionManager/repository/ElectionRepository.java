package main.ElectionManager.repository;

import main.ElectionManager.model.Election;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElectionRepository extends JpaRepository<Election, Integer> {
}
