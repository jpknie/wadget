package com.jn.wadget.computeclient

import io.ktor.client.engine.js.Js
import io.ktor.client.engine.HttpClientEngineFactory

actual fun provideEngine(): HttpClientEngineFactory<*> = Js