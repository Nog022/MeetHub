package com.git.Nog022.MeetHub.repository;

import com.git.Nog022.MeetHub.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    @Query("SELECT r FROM Reservation r " +
            "WHERE r.institution.id = :id " +
            "AND (:date IS NULL OR FUNCTION('DATE', r.date) = :date) " +
            "AND (:capacity IS NULL OR r.room.capacity >= :capacity) " +
            "AND (:roomName IS NULL OR LOWER(r.room.name) LIKE LOWER(CONCAT('%', :roomName, '%'))) " +
            "AND (:localName IS NULL OR LOWER(r.room.local.name) LIKE LOWER(CONCAT('%', :localName, '%')))")
    List<Reservation> reservationByFilter(
            @Param("id") Long id,
            @Param("date") LocalDate date,
            @Param("capacity") Integer capacity,
            @Param("roomName") String roomName,
            @Param("localName") String localName


    );
}