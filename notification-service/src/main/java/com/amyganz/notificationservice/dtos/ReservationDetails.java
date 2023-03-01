package com.amyganz.notificationservice.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
@JsonPropertyOrder({"id", "user", "hotel", "room", "payment", "status", "night_spent", "check_in_date", "check_out_date"})
public class ReservationDetails implements Serializable {
    private String name;
    private String email;
    @JsonProperty
    private String hotel_name;
    @JsonProperty
    private String room_type;
    @JsonProperty
    private Integer room_number;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Jakarta")
    @JsonProperty("check_in_date")
    private Date check_in_date;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Jakarta")
    @JsonProperty("check_out_date")
    private Date check_out_date;
    @JsonProperty
    private Integer total_payment;
    @JsonProperty
    private String status;
    @JsonProperty
    private String payment;

//    @JsonProperty("user")
//    private T user;
//    @JsonProperty("hotel")
//    private T hotel;
//    @JsonProperty("room")
//    private T room;
//    @JsonProperty("payment")
//    private PaymentMethod payment;
//    @JsonProperty("status")
//    private ReservationStatus status;
//    @JsonProperty("night_spent")
//    private Long nightSpent;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Jakarta")
//    @JsonProperty("check_in_date")
//    private Date checkInDate;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Jakarta")
//    @JsonProperty("check_out_date")
//    private Date checkOutDate;
}
