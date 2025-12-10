package com.git.Nog022.MeetHub.service.impl;

import com.git.Nog022.MeetHub.dto.ListReservarionDTO;
import com.git.Nog022.MeetHub.dto.ReservationDTO;

import com.git.Nog022.MeetHub.entity.*;
import com.git.Nog022.MeetHub.enums.UserRole;
import com.git.Nog022.MeetHub.repository.ReservationRepository;
import com.git.Nog022.MeetHub.repository.RoomRepository;
import com.git.Nog022.MeetHub.service.InstitutionService;
import com.git.Nog022.MeetHub.service.ReservationService;
import com.git.Nog022.MeetHub.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceImplTest {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private InstitutionService institutionService;

    @Mock
    private UserService userService;

    @InjectMocks
    private ReservationServiceImpl  reservationService;

    private ReservationDTO dto;
    private Room room;
    private User user;
    private Reservation reservation;


    @BeforeEach
    void setUp() {

        dto = new ReservationDTO(
                "Rodrigo",
                1L,
                LocalDateTime.of(2025, 1, 1, 0, 0),
                LocalTime.of(10, 0),
                LocalTime.of(12, 0),
                "Reunião",
                10L
        );


        Institution institution = new Institution();
        institution.setId(10L);
        institution.setName("UFF");


        Local local = new Local();
        local.setId(5L);
        local.setName("Bloco A");
        local.setCep("24210-200");
        local.setAddress("Rua X");
        local.setNeighborhood("Boa Viagem");
        local.setCity("Niterói");
        local.setState("RJ");
        local.setNumber("100");
        local.setComplement("Sala 2");
        local.setInstitution(institution);


        room = new Room();
        room.setId(1L);
        room.setName("Sala 1");
        room.setCapacity(10);
        room.setLocal(local);
        room.setResources(new ArrayList<>());
        room.setReservations(new ArrayList<>());

        user = new User();
        user.setId(99L);
        user.setName("User X");
        user.setEmail("userx@gmail.com");
        user.setCpf("12345678900");
        user.setRole(UserRole.USER);


        reservation = new Reservation();
        reservation.setId(123);
        reservation.setPersonName("Rodrigo");
        reservation.setRoom(room);
        reservation.setUser(user);
        reservation.setEventDescription("Reunião");
        reservation.setStartTime(LocalTime.of(10, 0));
        reservation.setEndTime(LocalTime.of(12, 0));
        reservation.setDate(LocalDateTime.of(2025, 1, 1, 0, 0));
        reservation.setInstitution(institution);
    }



    @Test
    void save() throws NoSuchMethodException {
        Method checkMethod = ReservationServiceImpl.class.getDeclaredMethod("checkReservation", ReservationDTO.class);
        checkMethod.setAccessible(true);
        ReservationServiceImpl spyService = spy(reservationService);

        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        when(institutionService.findById(10L)).thenReturn(any());
        when(userService.findById(99L)).thenReturn(user);
        when(reservationRepository.save(any())).thenReturn(reservation);

        ReservationDTO result = spyService.save(dto, 99L);

        Assertions.assertEquals(dto, result);
        verify(reservationRepository).save(any(Reservation.class));
        verify(roomRepository).save(room);
    }





    @Test
    void deleteAdminCanDelete() {
        when(reservationRepository.findById(123))
                .thenReturn(Optional.of(reservation));

        reservationService.delete(99L, 123, "ADMIN");

        verify(reservationRepository).delete(reservation);
    }

    @Test
    void deleteUserOwnerCanDelete() {
        when(reservationRepository.findById(123))
                .thenReturn(Optional.of(reservation));

        reservationService.delete(99L, 123, "USER");

        verify(reservationRepository).delete(reservation);
    }




    @Test
    void updateReservationExistsSuccess() {

        when(reservationRepository.findById(123))
                .thenReturn(Optional.of(reservation));

        reservationService.update(reservation);

        verify(reservationRepository).save(reservation);
    }

    @Test
    void updateReservationNotFound() {
        when(reservationRepository.findById(123))
                .thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(
                ResponseStatusException.class,
                () -> reservationService.update(reservation)
        );

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void update_MustKeepFoundId() {
        Reservation reservationfound = new Reservation();
        reservationfound.setId(999);
        reservationfound.setUser(user);
        reservationfound.setRoom(room);

        when(reservationRepository.findById(123))
                .thenReturn(Optional.of(reservationfound));

        reservationService.update(reservation);

        assertEquals(999, reservation.getId());
        verify(reservationRepository).save(reservation);
    }

    //List


    @Test
    void listReservations() {

        reservationService.listReservations();
    }

    @Test
    void listReservationsByInstitution() {


        List<Reservation> mockList = new ArrayList<>();
        mockList.add(reservation); // já configurado no @BeforeEach

        when(reservationRepository.reservationByFilter(
                10L,
                LocalDate.of(2025, 1, 1),
                10,
                "Sala 1",
                "Bloco A"
        )).thenReturn(mockList);

        List<ListReservarionDTO> result = reservationService.listReservationsByInstitution(
                10L,
                LocalDate.of(2025, 1, 1),
                10,
                "Sala 1",
                "Bloco A"
        );

        assertEquals(1, result.size());

        ListReservarionDTO dtoResult = result.get(0);


        assertEquals(reservation.getId(), dtoResult.id());
        assertEquals(reservation.getPersonName(), dtoResult.personName());
        assertEquals(reservation.getDate(), dtoResult.date());

        assertEquals(reservation.getEventDescription(), dtoResult.eventDescription());


        verify(reservationRepository).reservationByFilter(
                10L,
                LocalDate.of(2025, 1, 1),
                10,
                "Sala 1",
                "Bloco A"
        );

    }

    @Test
    void reservationById() {
        when(reservationRepository.findById(123)).thenReturn(Optional.of(reservation));

        Reservation result = reservationService.reservationById(123);

        assertEquals(reservation, result);
        verify(reservationRepository).findById(123);
    }

    @Test
    void reservationByIdNotFound() {
        when(reservationRepository.findById(999)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(
                ResponseStatusException.class,
                () -> reservationService.reservationById(999)
        );

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        assertEquals("Reservation not find", ex.getReason());
        verify(reservationRepository).findById(999);
    }


    @Test
    void listReservationsByRoomAndDate() {
        when(reservationRepository.reservationByRoomAndDate(1L, dto.date()))
                .thenReturn(List.of(reservation));

        ResponseEntity<List<ReservationDTO>> response =
                reservationService.listReservationsByRoomAndDate(1L, dto.date());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void listReservationsByRoomAndDate2() {
        when(reservationRepository.reservationByRoomAndDate(1L, dto.date()))
                .thenReturn(new ArrayList<>());

        ResponseEntity<List<ReservationDTO>> response =
                reservationService.listReservationsByRoomAndDate(1L, dto.date());

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void listReservationsByRoomAndDateException() {
        when(reservationRepository.reservationByRoomAndDate(1L, dto.date()))
                .thenThrow(new RuntimeException("DB error"));

        ResponseEntity<List<ReservationDTO>> response =
                reservationService.listReservationsByRoomAndDate(1L, dto.date());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
    }

}