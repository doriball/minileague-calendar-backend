package io.doriball.moduleauth

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.config.EnableMongoAuditing

@EnableMongoAuditing
@SpringBootApplication(scanBasePackages = ["io.doriball"])
class ModuleAuthApplication

fun main(args: Array<String>) {
    runApplication<ModuleAuthApplication>(*args)
}
