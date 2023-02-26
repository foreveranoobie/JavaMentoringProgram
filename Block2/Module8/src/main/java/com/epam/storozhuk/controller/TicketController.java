package com.epam.storozhuk.controller;

import com.epam.storozhuk.dto.TicketDto;
import com.epam.storozhuk.entity.Ticket;
import com.epam.storozhuk.facade.BookingFacade;
import com.epam.storozhuk.service.TicketService;
import com.itextpdf.text.DocumentException;
import java.io.ByteArrayInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private BookingFacade bookingFacade;

    @GetMapping("/{id}")
    public String getTicketById(@PathVariable long id, Model model) {
        model.addAttribute("returnTicket", ticketService.getTicketById(id));
        return "/ticket/ticket";
    }

    @GetMapping(value = "/getForUser", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getTickedByUserId(@RequestParam long id, @RequestParam int pageNum,
            @RequestParam int count)
            throws DocumentException {
        ByteArrayInputStream bis = bookingFacade
                .getInputStreamOfBookedTickets(bookingFacade.getBookedTicketsForUser(id, pageNum, count));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=ticketsreport.pdf");
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    @GetMapping(value = "/getForEvent", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getTickedByEventId(@RequestParam long id, @RequestParam int pageNum,
            @RequestParam int count)
            throws DocumentException {
        ByteArrayInputStream bis = bookingFacade
                .getInputStreamOfBookedTickets(bookingFacade.getBookedTicketsForEvent(id, pageNum, count));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=ticketsreport.pdf");
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    @PutMapping("/book")
    public String bookTicket(@RequestBody TicketDto ticketDto) {
        Ticket returnedTicket = bookingFacade.bookTicket(ticketDto);
        return "redirect:/ticket/" + returnedTicket.getId();
    }

    @ExceptionHandler(ArrayIndexOutOfBoundsException.class)
    public ResponseEntity<String> handlePaginationError(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        return new ResponseEntity<String>("Wrong pagination parameter!", headers, HttpStatus.BAD_REQUEST);
    }
}
