package org.lab5.lab5_eventsystem.Controller;

import org.lab5.lab5_eventsystem.ApiResponse.ApiResponse;
import org.lab5.lab5_eventsystem.Model.Event;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;

@RestController
@RequestMapping("api/v1/event_system")
public class EventController {
    ArrayList<Event> events = new ArrayList<>();
    static int idCounter = 1;
    static LocalDate today_date = LocalDate.now();
    //---------------------Get---------------------
    @GetMapping("/get/events")
    public ApiResponse getEvents() {
        if (events.isEmpty()) {
            return new ApiResponse("404", "No events found");
        }else {
            return new ApiResponse("200","Event list:"+events);
        }
    }
    @GetMapping("get/event/{id}")
    public ApiResponse getEvent(@PathVariable int id) {
        for (Event event : events) {
            if (event.getId() == id){
                return new ApiResponse("200","Event with id "+id+" found: "+event);
            }
        }
        return new ApiResponse("404", "No event found with id: "+id);
    }
    //---------------------Post--------------------
    @PostMapping("/post/event")
    public ApiResponse postEvent(@RequestBody Event event) {
        if (event.getDescription()==null|| event.start_date==null || event.end_date==null){
            return new ApiResponse("400", "Invalid data, must have a description, start date and end date.");
        } else if (event.getCapacity()<=0) {
            return new ApiResponse("400", "Invalid data, must have a capacity greater than 0.");
        } else if (event.start_date.isAfter(event.end_date)) {
            return new ApiResponse("400", "Invalid data, event start date cannot be after end date.");
        } else if (event.start_date.isBefore(today_date)) {
            return new ApiResponse("400", "Invalid data, event start date cannot be in the past.");

        }
        event.setId(idCounter++);
        events.add(event);
        return new ApiResponse("200","Event added");
    }

    //---------------------Put---------------------
    @PutMapping("/update/event/{id}")
    public ApiResponse updateEvent(@PathVariable int id,@RequestBody Event event) {
        if (event.getDescription()==null|| event.start_date==null || event.end_date==null){
            return new ApiResponse("400", "Invalid data, must have a description, start date and end date.");
        } else if (event.getCapacity()<=0) {
            return new ApiResponse("400", "Invalid data, must have a capacity greater than 0.");
        }
        for (Event e : events) {
            if (e.getId() == id){
                event.setId(id);
                events.set(events.indexOf(e), event);
                return new ApiResponse("200","Event updated");
            }
        }
        return new ApiResponse("404", "No event found with id: "+id);
    }

    @PutMapping("/update/event/{id}/capacity/{new_capacity}")
    public ApiResponse updateEventCapacity(@PathVariable int id,@PathVariable int new_capacity) {
        if (new_capacity<=0) {
            return new ApiResponse("400", "Invalid data, must have a capacity greater than 0.");
        }
        for (Event e : events) {
            if (e.getId()==id){
                e.setCapacity(new_capacity);
                return new ApiResponse("200","Event capacity updated");
            }
        }
        return new ApiResponse("404", "No event found with id: "+id);
    }
    //---------------------Delete------------------
    @DeleteMapping("/delete/event/{id}")
    public ApiResponse deleteEvent(@PathVariable int id) {
        for (Event e : events) {
            if (e.getId() == id){
                events.remove(e);
                return new ApiResponse("200","Event deleted");
            }
        }
        return new ApiResponse("404", "No event found with id: "+id);
    }

}
