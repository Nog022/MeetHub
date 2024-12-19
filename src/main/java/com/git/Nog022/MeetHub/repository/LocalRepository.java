package com.git.Nog022.MeetHub.repository;

import com.git.Nog022.MeetHub.entity.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalRepository extends JpaRepository<Local, Integer> {
}
