package com.example.getinline.service;

import com.example.getinline.constant.ErrorCode;
import com.example.getinline.constant.PlaceType;
import com.example.getinline.domain.Place;
import com.example.getinline.dto.PlaceDTO;
import com.example.getinline.exception.GeneralException;
import com.example.getinline.repository.PlaceRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@DisplayName("비즈니스 로직 - 장소")
@ExtendWith(MockitoExtension.class)
class PlaceServiceTest {

    @InjectMocks
    private PlaceService sut;
    @Mock
    private PlaceRepository placeRepository;

    @DisplayName("장소를 검색하면, 결과를 출력하여 보여준다.")
    @Test
    void givenNothing_whenSearchingPlaces_thenReturnsEntirePlaceList() {
        // given
        given(placeRepository.findAll(any(Predicate.class)))
                .willReturn(List.of(
                        createPlace(PlaceType.COMMON, "레스토랑"),
                        createPlace(PlaceType.SPORTS, "체육관")
                ));

        // when
        List<PlaceDTO> list = sut.getPlaces(new BooleanBuilder());

        // then
        assertThat(list).hasSize(2);
        then(placeRepository).should().findAll(any(Predicate.class));
    }

    @DisplayName("장소를 검색하는데 에러가 발생한 경우, 줄서기 프로젝트 기본 에러로 전환하여 예외 던진다.")
    @Test
    void givenDataRelatedException_whenSearchingPlaces_thenThrowsGeneralException() {
        // given
        RuntimeException e = new RuntimeException("This is test.");
        given(placeRepository.findAll(any(Predicate.class))).willThrow(e);

        // when
        Throwable thrown = catchThrowable(() -> sut.getPlaces(new BooleanBuilder()));

        // then
        assertThat(thrown)
                .isInstanceOf(GeneralException.class)
                .hasMessageContaining(ErrorCode.DATA_ACCESS_ERROR.getMessage());
        then(placeRepository).should().findAll(any(Predicate.class));
    }

    @DisplayName("장소 ID로 존재하는 장소를 조회하면, 해당 장소 정보를 출력하여 보여준다.")
    @Test
    void givenPlaceId_whenSearchingExistingPlace_thenReturnsPlace() {
        // given
        long placeId = 1L;
        Place place = createPlace(PlaceType.SPORTS, "체육관");
        given(placeRepository.findById(placeId)).willReturn(Optional.of(place));

        // when
        Optional<PlaceDTO> result = sut.getPlace(placeId);

        // then
        assertThat(result).hasValue(PlaceDTO.of(place));
        then(placeRepository).should().findById(placeId);
    }

    @DisplayName("장소 ID로 장소를 조회하면, 빈 정보를 출력하여 보여준다.")
    @Test
    void givenPlaceId_whenSearchingNonexistentPlace_thenReturnsEmptyOptional() {
        // given
        long placeId = 2L;
        given(placeRepository.findById(placeId)).willReturn(Optional.empty());

        // when
        Optional<PlaceDTO> result = sut.getPlace(placeId);

        // then
        assertThat(result).isEmpty();
        then(placeRepository).should().findById(placeId);
    }

    @DisplayName("장소 ID로 장소를 조회하는데 데이터 관련 에러가 발생한 경우, 줄서기 프로젝트 기본 에러로 전환하여 예외 던진다.")
    @Test
    void givenDataRelatedException_whenSearchingPlace_thenThrowsGeneralException() {
        // given
        RuntimeException e = new RuntimeException("This is test.");
        given(placeRepository.findById(any())).willThrow(e);

        // when
        Throwable thrown = catchThrowable(() -> sut.getPlace(null));

        // then
        assertThat(thrown)
                .isInstanceOf(GeneralException.class)
                .hasMessageContaining(ErrorCode.DATA_ACCESS_ERROR.getMessage());
        then(placeRepository).should().findById(any());
    }

    @DisplayName("장소 정보를 주면, 장소를 생성하고 결과를 true 로 보여준다.")
    @Test
    void givenPlace_whenCreating_thenCreatesPlaceAndReturnsTrue() {
        // given
        Place place = createPlace(PlaceType.SPORTS, "체육관");
        given(placeRepository.save(any(Place.class))).willReturn(place);

        // when
        boolean result = sut.createPlace(PlaceDTO.of(place));

        // then
        assertThat(result).isTrue();
        then(placeRepository).should().save(any(Place.class));
    }

    @DisplayName("장소 정보를 주지 않으면, 생성 중단하고 결과를 false 로 보여준다.")
    @Test
    void givenNothing_whenCreating_thenAbortCreatingAndReturnsFalse() {
        // given

        // when
        boolean result = sut.createPlace(null);

        // then
        assertThat(result).isFalse();
        then(placeRepository).shouldHaveNoInteractions();
    }

    @DisplayName("장소 생성 중 데이터 예외가 발생하면, 줄서기 프로젝트 기본 에러로 전환하여 예외 던진다")
    @Test
    void givenDataRelatedException_whenCreating_thenThrowsGeneralException() {
        // given
        Place place = createPlace(PlaceType.SPORTS, "체육관");
        RuntimeException e = new RuntimeException("This is test.");
        given(placeRepository.save(any())).willThrow(e);

        // when
        Throwable thrown = catchThrowable(() -> sut.createPlace(PlaceDTO.of(place)));

        // then
        assertThat(thrown)
                .isInstanceOf(GeneralException.class)
                .hasMessageContaining(ErrorCode.DATA_ACCESS_ERROR.getMessage());
        then(placeRepository).should().save(any());
    }

    @DisplayName("장소 ID와 정보를 주면, 장소 정보를 변경하고 결과를 true 로 보여준다.")
    @Test
    void givenPlaceIdAndItsInfo_whenModifying_thenModifiesPlaceAndReturnsTrue() {
        // given
        long placeId = 1L;
        Place originalPlace = createPlace(PlaceType.SPORTS, "체육관");
        Place changedPlace = createPlace(PlaceType.PARTY, "무도회장");
        given(placeRepository.findById(placeId)).willReturn(Optional.of(originalPlace));
        given(placeRepository.save(changedPlace)).willReturn(changedPlace);

        // when
        boolean result = sut.modifyPlace(placeId, PlaceDTO.of(changedPlace));

        // then
        assertThat(result).isTrue();
        then(placeRepository).should().findById(placeId);
        then(placeRepository).should().save(changedPlace);
    }

    @DisplayName("장소 ID를 주지 않으면, 장소 정보 변경 중단하고 결과를 false 로 보여준다.")
    @Test
    void givenNoPlaceId_whenModifying_thenAbortModifyingAndReturnsFalse() {
        // given
        Place place = createPlace(PlaceType.SPORTS, "체육관");

        // when
        boolean result = sut.modifyPlace(null, PlaceDTO.of(place));

        // then
        assertThat(result).isFalse();
        then(placeRepository).shouldHaveNoInteractions();
    }

    @DisplayName("장소 ID만 주고 변경할 정보를 주지 않으면, 장소 정보 변경 중단하고 결과를 false 로 보여준다.")
    @Test
    void givenPlaceIdOnly_whenModifying_thenAbortModifyingAndReturnsFalse() {
        // given
        long placeId = 1L;

        // when
        boolean result = sut.modifyPlace(placeId, null);

        // then
        assertThat(result).isFalse();
        then(placeRepository).shouldHaveNoInteractions();
    }

    @DisplayName("장소 변경 중 데이터 오류가 발생하면, 줄서기 프로젝트 기본 에러로 전환하여 예외 던진다.")
    @Test
    void givenDataRelatedException_whenModifying_thenThrowsGeneralException() {
        // given
        long placeId = 1L;
        Place originalPlace = createPlace(PlaceType.SPORTS, "체육관");
        Place wrongPlace = createPlace(null, null);
        RuntimeException e = new RuntimeException("This is test.");
        given(placeRepository.findById(placeId)).willReturn(Optional.of(originalPlace));
        given(placeRepository.save(any())).willThrow(e);

        // when
        Throwable thrown = catchThrowable(() -> sut.modifyPlace(placeId, PlaceDTO.of(wrongPlace)));

        // then
        assertThat(thrown)
                .isInstanceOf(GeneralException.class)
                .hasMessageContaining(ErrorCode.DATA_ACCESS_ERROR.getMessage());
        then(placeRepository).should().findById(placeId);
        then(placeRepository).should().save(any());
    }

    @DisplayName("장소 ID를 주면, 장소 정보를 삭제하고 결과를 true 로 보여준다.")
    @Test
    void givenPlaceId_whenDeleting_thenDeletesPlaceAndReturnsTrue() {
        // given
        long placeId = 1L;
        willDoNothing().given(placeRepository).deleteById(placeId);

        // when
        boolean result = sut.removePlace(placeId);

        // then
        assertThat(result).isTrue();
        then(placeRepository).should().deleteById(placeId);
    }

    @DisplayName("장소 ID를 주지 않으면, 삭제 중단하고 결과를 false 로 보여준다.")
    @Test
    void givenNothing_whenDeleting_thenAbortsDeletingAndReturnsFalse() {
        // given

        // when
        boolean result = sut.removePlace(null);

        // then
        assertThat(result).isFalse();
        then(placeRepository).shouldHaveNoInteractions();
    }

    @DisplayName("장소 삭제 중 데이터 오류가 발생하면, 줄서기 프로젝트 기본 에러로 전환하여 예외 던진다.")
    @Test
    void givenDataRelatedException_whenDeleting_thenThrowsGeneralException() {
        // given
        long placeId = 0L;
        RuntimeException e = new RuntimeException("This is test.");
        willThrow(e).given(placeRepository).deleteById(placeId);

        // when
        Throwable thrown = catchThrowable(() -> sut.removePlace(placeId));

        // then
        assertThat(thrown)
                .isInstanceOf(GeneralException.class)
                .hasMessageContaining(ErrorCode.DATA_ACCESS_ERROR.getMessage());
        then(placeRepository).should().deleteById(placeId);
    }


    private Place createPlace(PlaceType placeType, String placeName) {
        return createPlace(1L, placeType, placeName);
    }

    private Place createPlace(
            long id,
            PlaceType placeType,
            String placeName
    ) {
        Place place = Place.of(
                placeType,
                placeName,
                "주소 테스트",
                "010-1234-5678",
                24,
                "마스크 꼭 착용하세요"
        );
        ReflectionTestUtils.setField(place, "id", id);

        return place;
    }

}
