package com.amyganz.reservationservice.services.impl;

import com.amyganz.reservationservice.dtos.*;
import com.amyganz.reservationservice.entities.Invoice;
import com.amyganz.reservationservice.entities.PaymentMethod;
import com.amyganz.reservationservice.entities.Reservation;
import com.amyganz.reservationservice.entities.ReservationStatus;
import com.amyganz.reservationservice.exceptions.ServerException;
import com.amyganz.reservationservice.exceptions.reservation.*;
import com.amyganz.reservationservice.repositories.InvoiceRepository;
import com.amyganz.reservationservice.repositories.PaymentMethodRepository;
import com.amyganz.reservationservice.repositories.ReservationRepository;
import com.amyganz.reservationservice.repositories.ReservationStatusRepository;
import com.amyganz.reservationservice.services.ReservationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@ConfigurationProperties(prefix = "app")
@Service
@AllArgsConstructor
public class ReservationImpl implements ReservationService {
    private ReservationRepository reservationRepository;
    private PaymentMethodRepository paymentMethodRepository;
    private ReservationStatusRepository reservationStatusRepository;
    private InvoiceRepository invoiceRepository;
    private WebClient webClient;
    private ModelMapper modelMapper;
    private ObjectMapper objectMapper;

    @Value("${port.hotel}")
    private String PORT_HOTEL;
    @Value("${port.user}")
    private String PORT_USER;

    @Autowired
    private KafkaTemplate<String, String> template;

    private ReservationDetails reservationDetails(Reservation reservation, RoomResponse roomResponse) {
        ReservationDetails reservationDetails = new ReservationDetails();
//        reservationDetails.setHotel_name(roomResponse.getHotel().getName());
        reservationDetails.setRoom_type(roomResponse.getRoomType().getName());
        reservationDetails.setRoom_number(roomResponse.getRoomNumber());
        reservationDetails.setCheck_in_date(reservation.getCheckInDate());
        reservationDetails.setCheck_out_date(reservation.getCheckOutDate());
        reservationDetails.setTotal_payment(reservation.getTotalPayment());

        return reservationDetails;
    }
    private ReservationResponse convertToResponse(Reservation reservation) {return modelMapper.map(reservation, ReservationResponse.class);}
    private ReservationShortResponse convertToShortResponse(Reservation reservation) {return modelMapper.map(reservation, ReservationShortResponse.class);}

    private PaginateResponse getPaginateResponse(Page<Reservation> reservations) {
        List<ReservationShortResponse> reservationList = new ArrayList<>();
        for (Reservation reserve : reservations.getContent()) {
            ReservationShortResponse reserveDTO = modelMapper.map(reserve, ReservationShortResponse.class);
            reservationList.add(reserveDTO);
        }
        return PaginateResponse.builder()
                .items(Collections.singletonList(reservationList))
                .totalItems(reservations.getTotalElements())
                .totalPages(reservations.getTotalPages())
                .currentPage(reservations.getNumber()).build();
    }

    @Override
    public PaginateResponse getAllReservation(Pageable pageable) {
        Page<Reservation> reservations = reservationRepository.findAll(pageable);
        return getPaginateResponse(reservations);
    }

    @Override
    public PaginateResponse getReservationsByUserEquals(Pageable pageable, Integer id) {
        Page<Reservation> reservations = reservationRepository.findReservationsByUserEquals(pageable, id);
        return getPaginateResponse(reservations);

    }

    @Override
    public PaginateResponse getReservationsByHotelEquals(Pageable pageable, Integer hotelId) {

        Page<Reservation> reservations = reservationRepository.findReservationsByHotelEquals(pageable, hotelId);
        return getPaginateResponse(reservations);
    }

    @Override
    public ReservationResponse getReservationById(Long id) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(id);
        if (!reservationOptional.isPresent()) {
            throw new ReservationNotFound();
        }
        Reservation reservations = reservationOptional.get();
        ReservationResponse reservationResponse = modelMapper.map(reservations, ReservationResponse.class);
        ResponseDTO<UserResponse> monoUser = new ResponseDTO<>();
        try {
            monoUser = webClient.get()
                    .uri(PORT_USER + "api/v1/user/" + reservations.getUser())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(ResponseDTO.class)
                    .block();
        } catch (Exception e) {
            String[] code = e.getMessage().split(" ");
            if (code[0].equals("404")) {
                throw new UserNotExists();
            } else if (code[0].equals("Connection")) {
                throw new ServerException("Connection Refused");
            }
        }
        UserResponse resUser = objectMapper.convertValue(monoUser.getData(), UserResponse.class);

        ResponseDTO<HotelResponse> monoHotel = new ResponseDTO<>();
        try {
            monoHotel = webClient.get()
                    .uri(PORT_HOTEL + "api/v1/hotel/id/" + reservations.getHotel())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(ResponseDTO.class)
                    .block();
        } catch (Exception e) {
            String[] code = e.getMessage().split(" ");
            if (code[0].equals("404")) {
                throw new ReservationNotFoundHotel();
            } else if (code[0].equals("Connection")) {
                throw new ServerException("Connection Refused");
            }
        }
        HotelResponse resHotel = objectMapper.convertValue(monoHotel.getData(), HotelResponse.class);


        ResponseDTO<HotelResponse> monoRoom = new ResponseDTO<>();
        try {
            monoRoom = webClient.get()
                    .uri(PORT_HOTEL + "api/v1/room/id/" + reservations.getRoom())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(ResponseDTO.class)
                    .block();
        } catch (Exception e) {
            String[] code = e.getMessage().split(" ");
            if (code[0].equals("404")) {
                throw new ReservationNotFoundRoom();
            } else if (code[0].equals("Connection")) {
                throw new ServerException("Connection Refused");
            }
        }

        RoomResponse resRoom = objectMapper.convertValue(monoRoom.getData(), RoomResponse.class);

        reservationResponse.setUser(resUser);
        reservationResponse.setHotel(resHotel);
        reservationResponse.setRoom(resRoom);
        return reservationResponse;
    }

    @Override
    public ReservationRequest addReservation(ReservationRequest reservationRequest) {
        Optional<Reservation> reservationOptional = reservationRepository.findReservationByUserEqualsAndRoomEquals(reservationRequest.getUser(), reservationRequest.getRoom());
        if (reservationOptional.isPresent()) {
            if (reservationOptional.get().getReservationStatus().getId() != 3L) {
                throw new ReservationAlreadyExists();
            }
        }

        ResponseDTO<HotelResponse> monoHotel = new ResponseDTO<>();
        try {
          monoHotel = webClient.get()
                    .uri(PORT_HOTEL + "api/v1/hotel/id/" + reservationRequest.getHotel())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(ResponseDTO.class)
                    .block();
        } catch (Exception e) {
            String[] code = e.getMessage().split(" ");
            if (code[0].equals("404")) {
                throw new ReservationNotFoundHotel();
            } else if (code[0].equals("Connection")) {
                throw new ServerException("Connection Refused");
            }
        }

        ResponseDTO<UserResponse> monoUser = new ResponseDTO<>();
        try {
             monoUser = webClient.get()
                    .uri(PORT_USER + "api/v1/user/" + reservationRequest.getUser())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(ResponseDTO.class)
                    .block();
        } catch (Exception e) {
            String[] code = e.getMessage().split(" ");
            if (code[0].equals("404")) {
                throw new ReservationNotFoundUser();
            } else if (code[0].equals("Connection")) {
                throw new ServerException("Connection Refused");
            }
        }

        ResponseDTO<HotelResponse> monoRoom = new ResponseDTO<>();
        try {
            monoRoom = webClient.get()
                    .uri(PORT_HOTEL + "api/v1/room/id/" + reservationRequest.getRoom())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(ResponseDTO.class)
                    .block();
        } catch (Exception e) {
            String[] code = e.getMessage().split(" ");
            if (code[0].equals("404")) {
                throw new ReservationNotFoundRoom();
            } else if (code[0].equals("Connection")) {
                throw new ServerException("Connection Refused");
            }
        }

        RoomResponse resRoom = objectMapper.convertValue(monoRoom.getData(), RoomResponse.class);
        UserResponse resUser = objectMapper.convertValue(monoUser.getData(), UserResponse.class);
        HotelResponse resHotel = objectMapper.convertValue(monoHotel.getData(), HotelResponse.class);

        if (!resRoom.getAvailable()) {
            throw new ReservationRoomNotAvailable();
        }

        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        Reservation reservation = modelMapper.map(reservationRequest, Reservation.class);
        reservationRequest.setNightSpent(TimeUnit.DAYS.convert(reservation.getCheckOutDate().getTime() - reservation.getCheckInDate().getTime(), TimeUnit.MILLISECONDS));
        reservationRequest.setTotalPayment(resRoom.getRoomType().getCharge());

        reservation.setId(null);
        reservation.setNightSpent(reservationRequest.getNightSpent());
        reservation.setTotalPayment(reservationRequest.getTotalPayment());
        reservationRepository.save(reservation);

        ReservationDetails reservationDetails = reservationDetails(reservation, resRoom);
        reservationDetails.setStatus("Waiting for payment");
        reservationDetails.setHotel_name(resHotel.getName());
        reservationDetails.setName(resUser.getFullname());
        reservationDetails.setEmail(resUser.getEmail());

        template.send("new-reservation", reservationDetails.toString());

        return reservationRequest;
    }

    @Override
    public ReservationResponse updateReservation(ReservationRequest reservationRequest, Long id) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);

        if (optionalReservation.isEmpty()) {
            throw new ReservationNotFound();
        }

        Reservation reservation = optionalReservation.get();
        modelMapper.map(reservationRequest, reservation);
        Reservation updatedReservation = reservationRepository.save(reservation);
        return convertToResponse(updatedReservation);
    }

    @Override
    public void cancelReservation(Long id) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);

        if (optionalReservation.isEmpty()) {
            throw new ReservationNotFound();
        } else if (optionalReservation.get().getReservationStatus().getId() == 3L) {
            throw new ReservationAlreadyCanceled();
        }

        Optional<ReservationStatus> reservationStatusOptional = reservationStatusRepository.findById(3L);

        Reservation reservation = optionalReservation.get();
        reservation.setReservationStatus(reservationStatusOptional.get());
        reservationRepository.save(reservation);

        ResponseDTO<HotelResponse> monoRoom = webClient.get()
                .uri(PORT_HOTEL + "api/v1/room/id/" + reservation.getRoom())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ResponseDTO.class)
                .block();
        RoomResponse resRoom = objectMapper.convertValue(monoRoom.getData(), RoomResponse.class);

        ResponseDTO<UserResponse> monoUser = new ResponseDTO<>();
        try {
            monoUser = webClient.get()
                    .uri(PORT_USER + "api/v1/user/" + reservation.getUser())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(ResponseDTO.class)
                    .block();
        } catch (Exception e) {
            String[] code = e.getMessage().split(" ");
            if (code[0].equals("404")) {
                throw new ReservationNotFoundUser();
            } else if (code[0].equals("Connection")) {
                throw new ServerException("Connection Refused");
            }
        }
        UserResponse resUser = objectMapper.convertValue(monoUser.getData(), UserResponse.class);

        ResponseDTO<HotelResponse> monoHotel = webClient.get()
                .uri(PORT_HOTEL + "api/v1/hotel/id/" + reservation.getRoom())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ResponseDTO.class)
                .block();
        HotelResponse resHotel = objectMapper.convertValue(monoHotel.getData(), HotelResponse.class);

        ReservationDetails reservationDetails = reservationDetails(reservation, resRoom);
        reservationDetails.setName(resUser.getFullname());
        reservationDetails.setEmail(resUser.getEmail());
        reservationDetails.setHotel_name(resHotel.getName());
        reservationDetails.setStatus("Canceled");

        template.send("cancel-reservation", reservationDetails.toString());

    }

    @Override
    public void paidReservation(Long reservedId, Long paymentId) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(reservedId);

        if (reservationOptional.isEmpty()) {
            throw new ReservationNotFound();
        } else if (reservationOptional.get().getReservationStatus().getId() == 3L) {
            throw new ReservationAlreadyCanceled();
        } else if (reservationOptional.get().getReservationStatus().getId() == 2L) {
            throw new ReservationAlreadyPaid();
        }

        Optional<ReservationStatus> reservationStatusOptional = reservationStatusRepository.findById(2L);
        Optional<PaymentMethod> paymentMethodOptional = paymentMethodRepository.findById(paymentId);
        if (reservationStatusOptional.isEmpty()) {
            throw new ReservationNotFoundPayment();
        }

        Reservation reservation = reservationOptional.get();
        //

        //
        reservation.setReservationStatus(reservationStatusOptional.get());
        try {
            reservationRepository.save(reservation);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        ResponseDTO<HotelResponse> monoRoom = webClient.get()
                .uri(PORT_HOTEL + "api/v1/room/id/" + reservation.getRoom())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ResponseDTO.class)
                .block();
        RoomResponse resRoom = objectMapper.convertValue(monoRoom.getData(), RoomResponse.class);

        ResponseDTO<UserResponse> monoUser = new ResponseDTO<>();
        try {
            monoUser = webClient.get()
                    .uri(PORT_USER + "api/v1/user/" + reservation.getUser())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(ResponseDTO.class)
                    .block();
        } catch (Exception e) {
            String[] code = e.getMessage().split(" ");
            if (code[0].equals("404")) {
                throw new ReservationNotFoundUser();
            } else if (code[0].equals("Connection")) {
                throw new ServerException("Connection Refused");
            }
        }
        UserResponse resUser = objectMapper.convertValue(monoUser.getData(), UserResponse.class);


        ResponseDTO<HotelResponse> monoHotel = webClient.get()
                .uri(PORT_HOTEL + "api/v1/hotel/id/" + reservation.getHotel())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ResponseDTO.class)
                .block();
        HotelResponse resHotel = objectMapper.convertValue(monoHotel.getData(), HotelResponse.class);

        ReservationDetails reservationDetails = reservationDetails(reservation, resRoom);
        reservationDetails.setStatus("Paid");
        reservationDetails.setHotel_name(resHotel.getName());
        reservationDetails.setPayment(paymentMethodOptional.get().getName());
        reservationDetails.setName(resUser.getFullname());
        reservationDetails.setEmail(resUser.getEmail());

        template.send("paid-reservation", reservationDetails.toString());

        InvoiceRequest newInvoice = new InvoiceRequest(paymentMethodOptional.get(), reservation);
        Invoice invoice = modelMapper.map(newInvoice, Invoice.class);

        try {
            invoiceRepository.save(invoice);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        try {

            Mono<ClientResponse> response = webClient.put()
                    .uri("localhost:8083/api/v1/room/unavailabling/" + reservation.getRoom())
                    .exchange();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public void deleteReservation(Long id) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);

        if (optionalReservation.isEmpty()) {
            throw new ReservationNotFound();
        }

        Reservation reservation = optionalReservation.get();
        reservation.setDeletedAt(LocalDateTime.now());
        reservationRepository.save(reservation);
    }
}
