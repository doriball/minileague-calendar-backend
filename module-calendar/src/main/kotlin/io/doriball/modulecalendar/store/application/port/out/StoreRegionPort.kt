package io.doriball.modulecalendar.store.application.port.out

import io.doriball.modulecore.domain.store.StoreRegion

interface StoreRegionPort {

    fun getStoreRegions(): List<StoreRegion>

}