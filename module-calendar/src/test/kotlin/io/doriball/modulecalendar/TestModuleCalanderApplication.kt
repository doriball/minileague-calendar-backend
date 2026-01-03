package io.doriball.modulecalendar

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
    fromApplication<ModuleCalanderApplication>().with(TestcontainersConfiguration::class).run(*args)
}
