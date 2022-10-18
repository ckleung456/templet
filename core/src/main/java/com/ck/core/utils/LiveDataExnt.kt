package com.ck.core.utils

import android.os.Looper
import androidx.annotation.MainThread
import androidx.lifecycle.*

@MainThread
fun <T> LiveData<T>.observe(lifecycleOwner: LifecycleOwner, fn: (T) -> Unit) = observeNullable(lifecycleOwner) {
    if (it != null) fn(it)
}

@MainThread
fun <T> LiveData<T>.observeNullable(lifecycleOwner: LifecycleOwner, fn: (T?) -> Unit) {
    observe(lifecycleOwner, Observer {
        fn(it)
    })
}

@MainThread
fun <T> LiveData<Event<T>>.observeEvent(lifecycleOwner: LifecycleOwner, fn: (T) -> Unit) {
    observe(lifecycleOwner, Observer { event ->
        event?.handleContent {
            fn(it)
        }
    })
}

@MainThread
fun LiveData<Event<Unit>>.observeEvent(lifecycleOwner: LifecycleOwner, fn: () -> Unit) {
    observe(lifecycleOwner, Observer { event ->
        event?.handleContent {
            fn()
        }
    })
}

@MainThread
fun <T> LiveData<Event<T>>.observeEventForever(fn: (T) -> Unit) {
    observeForever { event ->
        event?.handleContent {
            fn(it)
        }
    }
}

@MainThread
fun LiveData<Event<Unit>>.observeEventForever(fn: () -> Unit) {
    observeForever { event ->
        event?.handleContent {
            fn()
        }
    }
}

@MainThread
fun <T> LiveData<Event<T>>.observeEventForever(observer: Observer<T>) {
    observeForever { event ->
        event?.handleContent {
            observer.onChanged(it)
        }
    }
}

@MainThread
fun <T> LiveData<Event<T>>.observeNullableEventForever(observer: Observer<T>) {
    observeForever { event ->
        event?.handleContent {
            observer.onChanged(it)
        }
    }
}

fun <T> MutableLiveData<Event<T>>.fireEvent(eventValue: T) {
    if (Looper.myLooper() == Looper.getMainLooper()) {
        value = Event(eventValue)
    } else {
        postValue(Event(eventValue))
    }
}

fun MutableLiveData<Event<Unit>>.fireEvent() {
    if (Looper.myLooper() == Looper.getMainLooper()) {
        value = Event(Unit)
    } else {
        postValue(Event(Unit))
    }
}

fun <S, T> LiveData<S>.derived(f: S.() -> T) = MediatorLiveData<T>().also { mediator ->
    mediator.addSource(this) { it ->
        mediator.setValue(it.f())
    }
}

/**
 * filter events having a given value to Unit events
 */
fun <T> LiveData<Event<T>>.filterToUnit(value: T) = MediatorLiveData<Event<Unit>>().also { mediator ->
    mediator.addSource(this) { event ->
        if (event.peekContent() == value) {
            mediator.fireEvent()
        }
    }
}

/**
 * Used as a wrapper for data which is exposed via a LiveData tat represents an event.
 */
open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set //Allow external read but not write

    fun handleContent(fn: (T) -> Unit) {
        if (!hasBeenHandled) {
            fn(content)
            hasBeenHandled = true
        }
    }

    /**
     * Returns then content, even if it has already been handled
     */
    fun peekContent() = content
}