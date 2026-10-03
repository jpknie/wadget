package com.jn.wadget.computeclient

import io.ktor.client.engine.ios.Ios
import io.ktor.client.engine.HttpClientEngineFactory

actual fun provideEngine(): HttpClientEngineFactory<*> = Ios
