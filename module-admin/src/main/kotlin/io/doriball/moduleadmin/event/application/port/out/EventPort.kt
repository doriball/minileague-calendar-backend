package io.doriball.moduleadmin.event.application.port.out

import io.doriball.moduleadmin.event.common.enums.EventKeywordSearchType
import io.doriball.moduleadmin.event.domain.model.EventCreate
import io.doriball.moduleadmin.event.domain.model.EventUpdate
import io.doriball.modulecore.domain.event.Event

interface EventPort {

    fun getEvents(
        year: Int,
        month: Int,
        regionNo: Int?,
        official: Boolean?,
        searchType: EventKeywordSearchType?,
        keyword: String?
    ): List<Event>

    fun getEventDetail(eventId: String): Event?

    fun isPastEvent(eventId: String): Boolean

    fun createEvent(create: EventCreate)

    fun updateEvent(update: EventUpdate)

    fun deleteEvent(eventId: String)

}