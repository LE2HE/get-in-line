package com.example.getinline.dto;

import com.example.getinline.constant.EventStatus;
import com.example.getinline.domain.Event;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

public record EventDTO(
        @NotNull @Positive Long placeId,
        @NotBlank String eventName,
        @NotNull EventStatus eventStatus,
        @NotNull LocalDateTime eventStartDatetime,
        @NotNull LocalDateTime eventEndDatetime,
        @NotNull @PositiveOrZero Integer currentNumberOfPeople,
        @NotNull @Positive Integer capacity,
        String memo
) {
    public static EventDTO of(
            Long placeId,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDatetime,
            LocalDateTime eventEndDatetime,
            Integer currentNumberOfPeople,
            Integer capacity,
            String memo
    ) {
        return new EventDTO(
                placeId,
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
                event.getPlaceId(),
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
                eventDTO.placeId(),
                eventDTO.eventName(),
                eventDTO.eventStatus(),
                eventDTO.eventStartDatetime(),
                eventDTO.eventEndDatetime(),
                eventDTO.currentNumberOfPeople(),
                eventDTO.capacity(),
                eventDTO.memo()
        );
    }

    public Event toEntity() {
        return Event.of(
                placeId,
                eventName,
                eventStatus,
                eventStartDatetime,
                eventEndDatetime,
                currentNumberOfPeople,
                capacity,
                memo
        );
    }

    public Event updateEntity(Event event) {
        if (placeId != null) { event.setPlaceId(placeId); }
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
