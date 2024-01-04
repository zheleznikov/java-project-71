import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
		application
		checkstyle
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
		mavenCentral()
}

dependencies {
		implementation("info.picocli:picocli:4.7.5")
		implementation("com.fasterxml.jackson.core:jackson-databind:2.16.0")
		implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.16.0")

		implementation("commons-io:commons-io:2.13.0")
		testImplementation(platform("org.junit:junit-bom:5.10.1"))
		testImplementation("org.junit.jupiter:junit-jupiter")

}

application {
		mainClass = "hexlet.code.App"
}

tasks.getByName("run", JavaExec::class) {
		standardInput = System.`in`
}

tasks.test {
		useJUnitPlatform()
		// https://technology.lastminute.com/junit5-kotlin-and-gradle-dsl/
		testLogging {
				exceptionFormat = TestExceptionFormat.FULL
				events = mutableSetOf(TestLogEvent.FAILED, TestLogEvent.PASSED, TestLogEvent.SKIPPED)
				showStackTraces = true
				showCauses = true
				showStandardStreams = true
		}
}