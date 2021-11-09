package com.example.disneycodechallenge.tests.di

import androidx.test.espresso.idling.concurrent.IdlingThreadPoolExecutor
import com.example.disneycodechallenge.di.DispatchersModule
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadFactory
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DispatchersModule::class]
)
class TestDispatchersModule {

    private val threadFactory: ThreadFactory = object : ThreadFactory {
        private val mCount = AtomicInteger(1)
        override fun newThread(r: Runnable): Thread {
            return Thread(r, "AsyncTask #" + mCount.getAndIncrement())
        }
    }

    @Provides
    fun provideDispatcherIO(): CoroutineDispatcher =
        IdlingThreadPoolExecutor(
            "HiltThreadPoolExecutor",
            20,
            20,
            3,
            TimeUnit.SECONDS,
            LinkedBlockingQueue(),
            threadFactory
        ).asCoroutineDispatcher()
}