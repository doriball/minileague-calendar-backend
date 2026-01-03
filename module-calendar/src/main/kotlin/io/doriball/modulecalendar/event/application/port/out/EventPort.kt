package io.doriball.modulecalendar.event.application.port.out

import io.doriball.modulecore.domain.event.Event

interface EventPort {

    fun getEvents(year: Int, month: Int, regionNo: Int?): List<Event>
    fun getEventDetail(eventId: String): Event?

}