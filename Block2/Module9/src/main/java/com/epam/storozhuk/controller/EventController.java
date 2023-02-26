package com.epam.storozhuk.controller;

import com.epam.storozhuk.dto.EventDto;
import com.epam.storozhuk.entity.Event;
import com.epam.storozhuk.service.EventService;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/{id}")
    public String getEventById(@PathVariable long id, Model model) {
        model.addAttribute("returnEvent", eventService.getEventById(id));
        return "/event/event";
    }

    @PutMapping(value = "/create")
    public ModelAndView createEvent(@RequestBody EventDto eventDto) {
        LocalDateTime currentTime = LocalDateTime.now();
        Event toCreateEvent = Event.builder()
                .title(eventDto.getTitle())
                .date(eventDto.getDate())
                .createDate(currentTime)
                .updateDate(currentTime)
                .build();
        Event returnEvent = eventService.createEvent(toCreateEvent);
        return new ModelAndView("redirect:/event/" + returnEvent.getId());
    }

}
