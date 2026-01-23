package io.doriball.moduleadmin.unit.place.adapter.out.persistence

import io.doriball.moduleadmin.fixture.document.placeRegionDocumentFixture
import io.doriball.moduleadmin.place.adapter.out.persistence.PlaceRegionQueryPersistenceAdapter
import io.doriball.moduleadmin.place.adapter.out.persistence.repository.PlaceRegionMongoRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class PlaceRegionQueryPersistenceAdapterUnitTest {

    @InjectMocks
    lateinit var sut: PlaceRegionQueryPersistenceAdapter

    @Mock
    lateinit var repository: PlaceRegionMongoRepository

    @Test
    fun 지역_목록_조회_성공() {

        // given
        given(repository.findAll()).willReturn(listOf(placeRegionDocumentFixture()))

        // when
        val result = sut.getPlaceRegions()

        // then
        assert(result.size == 1)
        assert(result[0].name == "서울")

    }

}