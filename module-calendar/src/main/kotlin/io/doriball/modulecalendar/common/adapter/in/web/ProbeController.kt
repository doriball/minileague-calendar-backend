package io.doriball.modulecalendar.common.adapter.`in`.web

import io.doriball.modulecalendar.store.adapter.out.persistence.repository.StoreMongoRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping(ProbeController.BASE_PATH)
@RestController
class ProbeController(val repository: StoreMongoRepository) {

    companion object {
        const val BASE_PATH = "/v1/probe"
    }

    @GetMapping("/health")
    fun health() = "OK"

    @GetMapping("/ready")
    fun ready() = repository.count()

}