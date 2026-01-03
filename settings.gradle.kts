pluginManagement {
	repositories {
		maven { url = uri("https://repo.spring.io/snapshot") }
		gradlePluginPortal()
	}
}
rootProject.name = "minileague-calendar-backend"

include("module-admin")
include("module-api")
include("module-auth")
include("module-batch")
include("module-calendar")
include("module-core")
include("module-infrastructure")