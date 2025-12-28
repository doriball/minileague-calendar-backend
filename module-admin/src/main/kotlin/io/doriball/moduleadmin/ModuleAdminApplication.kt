package io.doriball.moduleadmin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.config.EnableMongoAuditing

@EnableMongoAuditing
@SpringBootApplication(scanBasePackages = ["io.doriball"])
class ModuleAdminApplication

fun main(args: Array<String>) {
    runApplication<ModuleAdminApplication>(*args)
}
