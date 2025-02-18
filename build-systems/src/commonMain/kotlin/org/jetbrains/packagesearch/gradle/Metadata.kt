package org.jetbrains.packagesearch.gradle

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonPrimitive

@Serializable
public data class GradleMetadata(
    val formatVersion: String,
    val component: Component,
    val createdBy: CreatedBy? = null,
    val variants: List<Variant>? = null
)

@Serializable
public data class Component(
    val group: String,
    val module: String,
    val version: String,
    val url: String? = null,
    val attributes: Map<String, JsonPrimitive>? = null
)

@Serializable
public data class CreatedBy(
    val gradle: Gradle
)

@Serializable
public data class Gradle(
    val version: String,
    val buildId: String? = null
)

@Serializable
public data class Variant(
    val name: String,
    val attributes: Map<String, JsonPrimitive>? = null,
    @SerialName("available-at") val availableAt: AvailableAt? = null,
    val dependencies: List<Dependency>? = null,
    val dependencyConstraints: List<DependencyConstraint>? = null,
    val files: List<File>? = null
)

@Serializable
public data class AvailableAt(
    val url: String,
    val group: String,
    val module: String,
    val version: String
)

@Serializable
public data class Dependency(
    val group: String,
    val module: String,
    val version: Version,
    val attributes: Map<String, JsonPrimitive>? = null,
    val endorseStrictVersion: Boolean? = null
)

@Serializable
public data class Version(
    val requires: String
)

@Serializable
public data class DependencyConstraint(
    val group: String,
    val module: String,
    val version: Version
)

@Serializable
public data class File(
    val name: String,
    val url: String,
    val size: Int,
    val sha512: String,
    val sha256: String,
    val sha1: String,
    val md5: String
)
