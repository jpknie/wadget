package com.jn.wadget.computeclient

import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.HttpClientEngineFactory

actual fun provideEngine(): HttpClientEngineFactory<*> = CIO