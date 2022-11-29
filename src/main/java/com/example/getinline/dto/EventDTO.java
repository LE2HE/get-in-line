package com.example.getinline.dto;

import com.example.getinline.constant.EventStatus;
import com.example.getinline.domain.Event;
import com.example.getinline.domain.Place;

import java.time.LocalDateTime;

public record EventDTO(
        Long id,
        PlaceDTO placeDTO,
        String eventName,
        EventStatus eventStatus,
        LocalDateTime eventStartDatetime,
        LocalDateTime eventEndDatetime,
        Integer currentNumberOfPeople,
        Integer capacity,
        String memo
) {
    public static EventDTO of(
            Long id,
            PlaceDTO placeDTO,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDatetime,
            LocalDateTime eventEndDatetime,
            Integer currentNumberOfPeople,
            Integer capacity,
            String memo
    ) {
        return new EventDTO(
                id,
                placeDTO,
                eventName,
                eventStatus,
                eventStartDatetime,
                eventEndDatetime,
                currentNumberOfPeople,
                capacity,
                memo
        );
    }

    public static EventDTO of(Event event) {
        return new EventDTO(
                event.getId(),
                PlaceDTO.of(event.getPlace()),
                event.getEventName(),
                event.getEventStatus(),
                event.getEventStartDatetime(),
                event.getEventEndDatetime(),
                event.getCurrentNumberOfPeople(),
                event.getCapacity(),
                event.getMemo()
        );
    }

    public static EventDTO from(EventDTO eventDTO) {
        if (eventDTO == null) { return null; }
        return EventDTO.of(
                eventDTO.id(),
                eventDTO.placeDTO(),
                eventDTO.eventName(),
                eventDTO.eventStatus(),
                eventDTO.eventStartDatetime(),
                eventDTO.eventEndDatetime(),
                eventDTO.currentNumberOfPeople(),
                eventDTO.capacity(),
                eventDTO.memo()
        );
    }

    public Event toEntity(Place place) {
        return Event.of(
                place,
                eventName,
                eventStatus,
                eventStartDatetime,
                eventEndDatetime,
                currentNumberOfPeople,
                capacity,
                memo
        );
    }

    public EventDTO toDTO() {
        return EventDTO.of(
                null,
                null,
                this.eventName(),
                this.eventStatus(),
                this.eventStartDatetime(),
                this.eventEndDatetime(),
                this.currentNumberOfPeople(),
                this.capacity(),
                this.memo()
        );
    }

    public Event updateEntity(Event event) {
        if (eventName != null) { event.setEventName(eventName); }
        if (eventStatus != null) { event.setEventStatus(eventStatus); }
        if (eventStartDatetime != null) { event.setEventStartDatetime(eventStartDatetime); }
        if (eventEndDatetime != null) { event.setEventEndDatetime(eventEndDatetime); }
        if (currentNumberOfPeople != null) { event.setCurrentNumberOfPeople(currentNumberOfPeople); }
        if (capacity != null) { event.setCapacity(capacity); }
        if (memo != null) { event.setMemo(memo); }

        return event;
    }

}
