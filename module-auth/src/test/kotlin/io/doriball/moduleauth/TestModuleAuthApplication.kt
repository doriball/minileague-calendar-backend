package io.doriball.moduleauth

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
    fromApplication<ModuleAuthApplication>().with(TestcontainersConfiguration::class).run(*args)
}
