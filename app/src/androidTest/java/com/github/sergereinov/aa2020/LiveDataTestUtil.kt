package com.github.sergereinov.aa2020

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

//orig. src:
// https://medium.com/androiddevelopers/unit-testing-livedata-and-other-common-observability-problems-bb477262eb04

//full code src:
// https://github.com/android/architecture-components-samples/blob/master/LiveDataSample/app/src/test/java/com/android/example/livedatabuilder/util/LiveDataTestUtil.kt

//code src:
// https://gist.github.com/JoseAlcerreca/35828c25fca123c8a115d6251cf3f45b#file-livedatatestutil-kt

/* Copyright 2019 Google LLC.
   SPDX-License-Identifier: Apache-2.0 */
fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
            data = o
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }

    this.observeForever(observer)

    // Don't wait indefinitely if the LiveData is not set.
    if (!latch.await(time, timeUnit)) {
        throw TimeoutException("LiveData value was never set.")
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}