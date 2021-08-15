package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class Test07VerifyingBehavior {
    private BookingService bookingService;
    private PaymentService paymentServiceMock;
    private RoomService roomServiceMock;
    private BookingDAO bookingDAOMock;
    private MailSender mailSenderMock;

    @BeforeEach
    void setup() {
        // mock() creates a dummy object of a given class
        this.paymentServiceMock = mock(PaymentService.class);
        this.roomServiceMock = mock(RoomService.class);
        this.bookingDAOMock = mock(BookingDAO.class);
        this.mailSenderMock = mock(MailSender.class);

        this.bookingService = new BookingService(paymentServiceMock, roomServiceMock, bookingDAOMock, mailSenderMock);
    }

    @Test
    void should_InvokePayment_When_Prepaid() {
        // given
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01),
                LocalDate.of(2020, 01, 05), 2, true);

        // when
        bookingService.makeBooking(bookingRequest);

        // then
        verify(paymentServiceMock, times(1)).pay(bookingRequest, 400.0);
        verifyNoMoreInteractions(paymentServiceMock);   // Check the method is only invoked once
    }

    @Test
    void should_InvokePayment_When_NotPrepaid() {
        // given
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01),
                LocalDate.of(2020, 01, 05), 2, false);

        // when
        bookingService.makeBooking(bookingRequest);

        // then
        verify(paymentServiceMock, never()).pay(any(), anyDouble());
    }
}
