plugins {
    id("application")
    id("org.openjfx.javafxplugin") version "0.0.8"
}
group = "org.example"
version = "1.0-SNAPSHOT"

application {
    mainClass.set("HelloFX") // TODO: Change as needed
}

repositories {
    mavenCentral()
}

javafx {
    version = "15.0.1"
    modules = listOf("javafx.controls", "javafx.fxml")
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

val jfxModules = listOf("controls", "fxml", "base", "graphics", "media", "web")
jfxModules.forEach {
    val osNameMap = mapOf("Linux" to "linux", "Mac" to "mac", "Windows" to "win")
    val currentOS = System.getProperty("os.name").split(" ").first()
    val osName = osNameMap[currentOS]
    val p = "org.openjfx:javafx-$it:${javafx.version}:$osName"
    println(p)
    dependencies.add("runtimeOnly", p)
}

tasks {
    test {
        useJUnitPlatform()
    }

    jar {
        manifest {
            attributes(mapOf("Main-Class" to "Launcher"))
        }
        from({
            configurations.runtimeClasspath.get()
                    .map {
                        if (it.isDirectory) it
                        else zipTree(it)
                    }
        })
    }

}
