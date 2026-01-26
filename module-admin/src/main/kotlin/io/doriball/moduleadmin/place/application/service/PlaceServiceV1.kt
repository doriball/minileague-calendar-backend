package io.doriball.moduleadmin.place.application.service

import io.doriball.moduleadmin.place.application.dto.PlaceDetailDto
import io.doriball.moduleadmin.place.application.dto.PlaceDto
import io.doriball.moduleadmin.place.application.dto.PlaceSummaryDto
import io.doriball.moduleadmin.place.application.port.`in`.PlaceUseCase
import io.doriball.moduleadmin.place.application.port.`in`.dto.*
import io.doriball.moduleadmin.place.application.port.out.PlacePort
import io.doriball.moduleadmin.place.common.exception.EventExistException
import io.doriball.moduleadmin.place.domain.PlaceCreate
import io.doriball.moduleadmin.place.domain.PlaceUpdate
import io.doriball.modulecore.shared.codes.SharedCacheName
import io.doriball.modulecore.shared.exception.NotFoundException
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Caching
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PlaceServiceV1(val placePort: PlacePort) : PlaceUseCase {

    override fun getPlaces(command: ReadPlacesCommand): Pair<List<PlaceDto>, Long> {
        val (stores, size) = placePort.getPlaces(
            page = command.page,
            size = command.size,
            keyword = command.keyword,
            regionNo = command.regionNo
        )
        return stores.map { PlaceDto.from(it) } to size
    }

    override fun getPlaceSummaries(command: ReadPlaceSummariesCommand): List<PlaceSummaryDto> {
        return placePort.getPlaceSummaries(command.regionNo).map { PlaceSummaryDto.from(it) }
    }

    override fun getPlaceDetail(command: ReadPlaceDetailCommand): PlaceDetailDto {
        val place = placePort.getPlaceDetail(command.placeId) ?: throw NotFoundException()
        return PlaceDetailDto.from(place)
    }

    @CacheEvict(value = [SharedCacheName.STORES], allEntries = true, condition = "#command.type.name() != 'ETC'")
    @Transactional
    override fun createPlace(command: CreatePlaceCommand) {
        placePort.createPlace(PlaceCreate.from(command))
    }

    @Caching(
        evict = [
            CacheEvict(value = [SharedCacheName.STORES], allEntries = true),
            CacheEvict(value = [SharedCacheName.STORE_DETAIL], key = "#placeId")
        ]
    )
    @Transactional
    override fun updatePlace(placeId: String, command: UpdatePlaceCommand) {
        placePort.updatePlace(placeId, PlaceUpdate.from(placeId, command))
    }

    @Caching(
        evict = [
            CacheEvict(value = [SharedCacheName.STORES], allEntries = true),
            CacheEvict(value = [SharedCacheName.STORE_DETAIL], key = "#placeId")
        ]
    )
    @Transactional
    override fun deletePlace(placeId: String) {
        if (placePort.isEventExist(placeId)) throw EventExistException()
        placePort.deletePlace(placeId)
    }

}