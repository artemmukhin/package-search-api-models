package org.jetbrains.packagesearch.api.v3.http

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.Url
import io.ktor.serialization.kotlinx.json.json
import org.jetbrains.packagesearch.api.v3.ApiPackage
import org.jetbrains.packagesearch.api.v3.ApiRepository
import org.jetbrains.packagesearch.api.v3.MavenHashLookupRequest
import org.jetbrains.packagesearch.api.v3.MavenHashLookupResponse
import org.jetbrains.packagesearch.api.v3.search.SearchParameters

class PackageSearchApiClient(val endpoints: PackageSearchEndpoints) {

    private val httpClient = HttpClient(DefaultEngine) {
        install(ContentNegotiation) {
            json()
        }
    }

    private suspend inline fun <reified T, reified R> defaultRequest(url: Url, body: T) =
        httpClient.get(url) {
            setBody(body)
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            header(HttpHeaders.Accept, ContentType.Application.Json)
        }.body<R>()

    private suspend inline fun <reified R> defaultRequest(url: Url) =
        httpClient.get(url) {
            header(HttpHeaders.Accept, ContentType.Application.Json)
        }.body<R>()

    suspend fun getKnownRepositories(): List<ApiRepository> =
        defaultRequest(endpoints.knownRepositories)

    suspend fun getPackageInfoByIds(ids: Set<String>): List<ApiPackage> =
        defaultRequest(endpoints.packageInfoByIds, GetPackageInfoRequest(ids))

    suspend fun getPackageInfoByIdHashes(ids: Set<String>): List<ApiPackage> =
        defaultRequest(endpoints.packageInfoByIdHashes, GetPackageInfoRequest(ids))

    suspend fun searchPackages(searchParameters: SearchParameters): List<ApiPackage> =
        defaultRequest(endpoints.searchPackages, searchParameters)

    suspend fun getMavenPackageInfoByFileHash(request: MavenHashLookupRequest): MavenHashLookupResponse =
        defaultRequest(endpoints.mavenPackageInfoByFileHash, request)

    suspend fun getScmByUrl(request: GetScmByUrlRequest): String? =
        defaultRequest(endpoints.getScmsByUrl, request)
}