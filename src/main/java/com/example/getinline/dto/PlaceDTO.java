package com.example.getinline.dto;

import com.example.getinline.constant.PlaceType;
import com.example.getinline.domain.Place;

public record PlaceDTO(
        Long id,
        PlaceType placeType,
        String placeName,
        String address,
        String phoneNumber,
        Integer capacity,
        String memo
) {
    public static PlaceDTO of(
            Long id,
            PlaceType placeType,
            String placeName,
            String address,
            String phoneNumber,
            Integer capacity,
            String memo) {
        return new PlaceDTO(id, placeType, placeName, address, phoneNumber, capacity, memo);
    }

    public static PlaceDTO of(Place place) {
        return new PlaceDTO(
                place.getId(),
                place.getPlaceType(),
                place.getPlaceName(),
                place.getAddress(),
                place.getPhoneNumber(),
                place.getCapacity(),
                place.getMemo()
        );
    }

    public static PlaceDTO from(PlaceDTO placeDto) {
        if (placeDto == null) { return null; }
        return PlaceDTO.of(
                placeDto.id(),
                placeDto.placeType(),
                placeDto.placeName(),
                placeDto.address(),
                placeDto.phoneNumber(),
                placeDto.capacity(),
                placeDto.memo()
        );
    }

    public Place toEntity() {
        return Place.of(placeType, placeName, address, phoneNumber, capacity, memo);
    }

    public Place updateEntity(Place place) {
        if (placeType != null) { place.setPlaceType(placeType); }
        if (placeName != null) { place.setPlaceName(placeName); }
        if (address != null) { place.setAddress(address); }
        if (phoneNumber != null) { place.setPhoneNumber(phoneNumber); }
        if (capacity != null) { place.setCapacity(capacity); }
        if (memo != null) { place.setMemo(memo); }

        return place;
    }

}
