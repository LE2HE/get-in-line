package com.example.getinline.controller.api;

import com.example.getinline.constant.PlaceType;
import com.example.getinline.dto.APIDataResponse;
import com.example.getinline.dto.PlaceDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
public class APIPlaceController {

    @GetMapping("/places")
    public APIDataResponse<List<PlaceDTO>> getPlaces() {
        return APIDataResponse.of(List.of(PlaceDTO.of(
                PlaceType.COMMON,
                "랄라배드민턴장",
                "서울시 강남구 강남대로 1234",
                "010-1234-5678",
                30,
                "신장개업"
        )));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/places")
    public APIDataResponse<Void> createPlace(@RequestBody PlaceDTO placeDTO) {
        return APIDataResponse.empty();
    }

    @GetMapping("/places/{placeId}")
    public APIDataResponse<PlaceDTO> getPlace(@PathVariable Long placeId) {
        if (placeId.equals(2L)) {
            return APIDataResponse.of(null);
        }

        return APIDataResponse.of(PlaceDTO.of(
                PlaceType.COMMON,
                "랄라배드민턴장",
                "서울시 강남구 강남대로 1234",
                "010-1234-5678",
                30,
                "신장개업"
        ));
    }

    @PutMapping("/places/{placeId}")
    public APIDataResponse<Void> modifyPlace(
            @PathVariable Long placeId,
            @RequestBody PlaceDTO placeDTO
    ) {
        return APIDataResponse.empty();
    }

    @DeleteMapping("/places/{placeId}")
    public APIDataResponse<Void> removePlace(@PathVariable Long placeId) {
        return APIDataResponse.empty();
    }

}
