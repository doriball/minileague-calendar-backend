package io.doriball.moduleadmin

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
    fromApplication<ModuleAdminApplication>().with(TestcontainersConfiguration::class).run(*args)
}
