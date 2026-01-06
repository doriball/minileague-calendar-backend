package io.doriball.modulebatch

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import kotlin.system.exitProcess

@SpringBootApplication(scanBasePackages = ["io.doriball"])
class ModuleBatchApplication

fun main(args: Array<String>) {
    val context = runApplication<ModuleBatchApplication>(*args)
    exitProcess(SpringApplication.exit(context))
}
