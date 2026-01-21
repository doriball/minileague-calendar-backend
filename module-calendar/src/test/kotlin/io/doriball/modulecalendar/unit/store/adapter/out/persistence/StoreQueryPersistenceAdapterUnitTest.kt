package io.doriball.modulecalendar.unit.store.adapter.out.persistence

import io.doriball.modulecalendar.fixture.document.storeDocumentFixture
import io.doriball.modulecalendar.fixture.document.storeRegionDocumentFixture
import io.doriball.modulecalendar.store.adapter.out.persistence.StoreQueryPersistenceAdapter
import io.doriball.modulecalendar.store.adapter.out.persistence.repository.StoreMongoRepository
import io.doriball.modulecalendar.store.adapter.out.persistence.repository.StoreRegionMongoRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.anyList
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import java.util.Optional

@ExtendWith(MockitoExtension::class)
class StoreQueryPersistenceAdapterUnitTest {
    
    @InjectMocks
    lateinit var sut: StoreQueryPersistenceAdapter
    
    @Mock
    lateinit var storeRepository: StoreMongoRepository
    
    @Mock
    lateinit var storeRegionRepository: StoreRegionMongoRepository
    
    @Test
    fun 매장_목록_조회_성공_지역_필터링_미적용() {
    
        // given
        val regionNo = null
        given(storeRepository.findAll()).willReturn(listOf(storeDocumentFixture()))
        given(storeRegionRepository.findByRegionNoIn(anyList())).willReturn(listOf(storeRegionDocumentFixture()))
        
        // when
        val result = sut.getStores(regionNo)
        
        // then
        assert(result.size == 1)
        assert(result[0].id == "store-1")
        assert(result[0].name == "포켓몬 카드샵 용산")
        assert(result[0].regionName == "서울")
        
    }
    
    @Test
    fun 매장_목록_조회_성공_지역_필터링_적용() {
    
        // given
        val regionNo = 1
        given(storeRepository.findByRegionNo(regionNo)).willReturn(listOf(storeDocumentFixture()))
        given(storeRegionRepository.findByRegionNo(regionNo)).willReturn(storeRegionDocumentFixture())

        // when
        val result = sut.getStores(regionNo)
        
        // then
        assert(result.size == 1)
        assert(result[0].id == "store-1")
        assert(result[0].name == "포켓몬 카드샵 용산")
        assert(result[0].regionName == "서울")

    }
    
    @Test
    fun 매장_단건_조회_성공() {
    
        // given
        val storeId = "store-1"
        given(storeRepository.findById(storeId)).willReturn(Optional.of(storeDocumentFixture()))
        given(storeRegionRepository.findByRegionNo(storeDocumentFixture().regionNo)).willReturn(storeRegionDocumentFixture())

        // when
        val result = sut.getStoreDetail(storeId)!!
        
        // then
        assert(result.id == "store-1")
        assert(result.name == "포켓몬 카드샵 용산")
        assert(result.regionName == "서울")
        
    }
    
    @Test
    fun 매장_단건_조회_NULL_매장이_없는_경우() {
    
        // given
        val storeId = "store-2"
        
        // when
        val result = sut.getStoreDetail(storeId)
        
        // then
        assert(result == null)
        
    }
    
    @Test
    fun 매장_단건_조회_NULL_지역이_없는_경우() {
    
        // given
        val storeId = "store-1"
        
        // when
        val result = sut.getStoreDetail(storeId)
        
        // then
        assert(result == null)
        
    }
    
}