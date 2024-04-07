package com.example.bachelordegreeproject.core.network

import com.example.bachelordegreeproject.core.util.constants.RflotEndpoint
import com.example.bachelordegreeproject.data.remote.request.AuthRequestModel
import com.example.bachelordegreeproject.data.remote.request.CheckEquipRequestModel
import com.example.bachelordegreeproject.data.remote.request.CheckZoneRequestModel
import com.example.bachelordegreeproject.data.remote.request.GetZonesRequestModel
import com.example.bachelordegreeproject.data.remote.request.StartSessionRequestModel
import com.example.bachelordegreeproject.data.remote.response.AuthResponseModel
import com.example.bachelordegreeproject.data.remote.response.CheckEquipResponseModel
import com.example.bachelordegreeproject.data.remote.response.CheckZoneResponseModel
import com.example.bachelordegreeproject.data.remote.response.GetZonesResponseModel
import com.example.bachelordegreeproject.data.remote.response.SessionResponseModel
import com.example.bachelordegreeproject.di.IoDispatcher
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class RflotHttpServiceImpl @Inject constructor(
    private val httpClient: RflotHttpClient,
    @IoDispatcher private val coroutineContext: CoroutineContext
) : RflotHttpService {

    override suspend fun auth(authParams: AuthRequestModel): Result<AuthResponseModel> =
        withContext(coroutineContext) {
            return@withContext try {
                Result.success(
                    httpClient().post {
                        url(path = RflotEndpoint.auth)
                        setBody(authParams)
                    }.body()
                )
            } catch (e: Exception) {
                Timber.e("Failed to auth: $e")
                Result.failure(e)
            }
        }

    override suspend fun startSession(params: StartSessionRequestModel): Result<SessionResponseModel> =
        withContext(coroutineContext) {
            return@withContext try {
                Result.success(
                    httpClient().post {
                        url(path = RflotEndpoint.getCheck)
                        setBody(params)
                    }.body()
                )
            } catch (e: Exception) {
                Timber.e("Failed to start session: $e")
                Result.failure(e)
            }
        }

    override suspend fun getZones(params: GetZonesRequestModel): Result<GetZonesResponseModel> =
        withContext(coroutineContext) {
            return@withContext try {
                Result.success(
                    httpClient().post {
                        url(path = RflotEndpoint.getZones)
                        setBody(params)
                    }.body()
                )
            } catch (e: Exception) {
                Timber.e("Failed to get zones: $e")
                Result.failure(e)
            }
        }

    override suspend fun checkZone(params: CheckZoneRequestModel): Result<CheckZoneResponseModel> =
        withContext(coroutineContext) {
            return@withContext try {
                Result.success(
                    httpClient().post {
                        url(path = RflotEndpoint.checkZone)
                        setBody(params)
                    }.body()
                )
            } catch (e: Exception) {
                Timber.e("Failed to check zone: $e")
                Result.failure(e)
            }
        }

    override suspend fun checkEquip(params: CheckEquipRequestModel): Result<CheckEquipResponseModel> =
        withContext(coroutineContext) {
            return@withContext try {
                Result.success(
                    httpClient().post {
                        url(path = RflotEndpoint.checkEquip)
                        setBody(params)
                    }.body()
                )
            } catch (e: Exception) {
                Timber.e("Failed to check equip: $e")
                Result.failure(e)
            }
        }
}
