package com.jn.wadget.computeclient

import io.ktor.client.engine.HttpClientEngineFactory

expect fun provideEngine(): HttpClientEngineFactory<*>