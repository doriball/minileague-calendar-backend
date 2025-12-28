package io.doriball.moduleadmin

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@Import(TestcontainersConfiguration::class)
@SpringBootTest
class ModuleAdminApplicationTests {

    @Test
    fun contextLoads() {
    }

}
