package io.doriball.modulecalendar.unit.place.adapter.out.persistence

import io.doriball.modulecalendar.fixture.document.placeDocumentFixture
import io.doriball.modulecalendar.fixture.document.placeRegionDocumentFixture
import io.doriball.modulecalendar.place.adapter.out.persistence.PlaceQueryPersistenceAdapter
import io.doriball.modulecalendar.place.adapter.out.persistence.repository.PlaceMongoRepository
import io.doriball.modulecalendar.place.adapter.out.persistence.repository.PlaceRegionMongoRepository
import io.doriball.modulecore.enums.PlaceType
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.anyList
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import java.util.Optional

@ExtendWith(MockitoExtension::class)
class PlaceQueryPersistenceAdapterUnitTest {
    
    @InjectMocks
    lateinit var sut: PlaceQueryPersistenceAdapter
    
    @Mock
    lateinit var placeRepository: PlaceMongoRepository
    
    @Mock
    lateinit var placeRegionRepository: PlaceRegionMongoRepository
    
    @Test
    fun 매장_목록_조회_성공_지역_필터링_미적용() {
    
        // given
        val regionNo = null
        given(placeRepository.findAllByType(PlaceType.STORE)).willReturn(listOf(placeDocumentFixture()))
        given(placeRegionRepository.findByRegionNoIn(anyList())).willReturn(listOf(placeRegionDocumentFixture()))
        
        // when
        val result = sut.getStores(regionNo)
        
        // then
        assert(result.size == 1)
        assert(result[0].id == "place-1")
        assert(result[0].name == "포켓몬 카드샵 용산")
        assert(result[0].regionName == "서울")
        
    }
    
    @Test
    fun 매장_목록_조회_성공_지역_필터링_적용() {
    
        // given
        val regionNo = 1
        given(placeRepository.findByRegionNoAndType(regionNo, PlaceType.STORE)).willReturn(listOf(placeDocumentFixture()))
        given(placeRegionRepository.findByRegionNo(regionNo)).willReturn(placeRegionDocumentFixture())

        // when
        val result = sut.getStores(regionNo)
        
        // then
        assert(result.size == 1)
        assert(result[0].id == "place-1")
        assert(result[0].name == "포켓몬 카드샵 용산")
        assert(result[0].regionName == "서울")

    }
    
    @Test
    fun 매장_단건_조회_성공() {
    
        // given
        val storeId = "place-1"
        given(placeRepository.findById(storeId)).willReturn(Optional.of(placeDocumentFixture()))
        given(placeRegionRepository.findByRegionNo(placeDocumentFixture().regionNo)).willReturn(placeRegionDocumentFixture())

        // when
        val result = sut.getStoreDetail(storeId)!!
        
        // then
        assert(result.id == "place-1")
        assert(result.name == "포켓몬 카드샵 용산")
        assert(result.regionName == "서울")
        
    }
    
    @Test
    fun 매장_단건_조회_NULL_매장이_없는_경우() {
    
        // given
        val storeId = "place-2"
        
        // when
        val result = sut.getStoreDetail(storeId)
        
        // then
        assert(result == null)
        
    }
    
    @Test
    fun 매장_단건_조회_NULL_지역이_없는_경우() {
    
        // given
        val storeId = "place-1"
        
        // when
        val result = sut.getStoreDetail(storeId)
        
        // then
        assert(result == null)
        
    }
    
}