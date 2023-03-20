package ru.Art3m1y.LinkCutter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.Art3m1y.LinkCutter.models.Link;

@Repository
public interface LinkRepo extends JpaRepository<Link, Long> {
}
