pluginManagement {
	repositories {
		maven { url = uri("https://repo.spring.io/snapshot") }
		gradlePluginPortal()
	}
}
rootProject.name = "minileague-calander-backend"

include("module-admin")
include("module-api")
include("module-auth")
include("module-calander")
include("module-core")
include("module-infrastructure")