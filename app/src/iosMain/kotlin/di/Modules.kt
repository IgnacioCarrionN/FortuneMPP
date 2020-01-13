package di

import io.ktor.client.HttpClient
import io.ktor.client.engine.ios.Ios

actual val client: HttpClient = HttpClient(Ios)