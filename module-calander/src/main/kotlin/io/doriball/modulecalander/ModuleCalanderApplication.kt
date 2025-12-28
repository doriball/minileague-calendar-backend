package io.doriball.modulecalander

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.config.EnableMongoAuditing

@EnableMongoAuditing
@SpringBootApplication(scanBasePackages = ["io.doriball"])
class ModuleCalanderApplication

fun main(args: Array<String>) {
    runApplication<ModuleCalanderApplication>(*args)
}
