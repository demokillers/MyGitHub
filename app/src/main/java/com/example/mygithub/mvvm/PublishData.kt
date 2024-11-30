package com.example.mygithub.mvvm

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer

abstract class PublishData<T> : LiveData<EventWrapper<T>>() {

    protected open fun publish(event: T) = runOnUI {
        val eventWrapper = EventWrapper(event)
        value = eventWrapper
        eventWrapper.hasBeenHandled = true
    }

    @Deprecated("typo fix", ReplaceWith("observe(lifecycleOwner, observer)"))
    fun observer(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
        observe(lifecycleOwner, observer)
    }

    @Deprecated("typo fix", ReplaceWith("observe(lifecycleOwner, observer)"))
    fun observer(lifecycleOwner: LifecycleOwner, observer: (T) -> Unit) {
        observe(lifecycleOwner, observer)
    }

    /**
     * 由于默认[LiveData]的激活时间onStart->onStop，**在[PublishData]中使用此方法有可能造成消息丢失**,
     * 因为[publish]方法会将消息同步标记为已处理。除非很清楚你的消息只会在onStart->onStop之间发送,
     * 否则建议使用[observeAlive], 该方法可以在注册之后, [onDestroy] 之前始终接收消息
     */
    open fun observe(lifecycleOwner: LifecycleOwner, observer: Observer<T>): Disposable {
        val mediator = createUnwrapMediator()

        mediator.observe(lifecycleOwner, observer)

        return RunnableDisposable {
            mediator.removeObserver(observer)
        }
    }

    open fun observe(lifecycleOwner: LifecycleOwner, observer: (T) -> Unit): Disposable {
        return observe(lifecycleOwner, Observer<T> { observer(it) })
    }

    fun observeDisposable(observer: Observer<T>): Disposable {
        val mediator = createUnwrapMediator()

        mediator.observeForever(observer)

        return RunnableDisposable {
            mediator.removeObserver(observer)
        }
    }

    fun observeDisposable(observer: (T) -> Unit): Disposable {
        return observeDisposable(Observer { observer(it) })
    }

    /**
     * 在onDestroy之前始终处于激活状态，没有onStart才激活的限制
     */
    open fun observeAlive(lifecycleOwner: LifecycleOwner, onUpdate: (T) -> Unit) {
        this.observeDisposable(onUpdate).bind(lifecycleOwner.lifecycle)
    }

    /**
     * 获取上一次事件缓存值，可能为空
     */
    fun peekEvent(): T? {
        return value?.peekContent()
    }

    @Deprecated(
        "不建议直接观察原始数据",
        ReplaceWith("observe(LifecycleOwner, (T) -> Unit)")
    )
    override fun observe(owner: LifecycleOwner, observer: Observer<in EventWrapper<T>>) {
        super.observe(owner, observer)
    }

    @Deprecated(
        "不建议直接观察原始数据",
        ReplaceWith("observeDisposable((T) -> Unit)")
    )
    override fun observeForever(observer: Observer<in EventWrapper<T>>) {
        super.observeForever(observer)
    }

    @Deprecated(
        "不建议直接修改原始数据",
        ReplaceWith("publish(T)")
    )
    override fun setValue(value: EventWrapper<T>?) {
        super.setValue(value)
    }

    @Deprecated(
        "不建议直接读取原始数据",
        ReplaceWith("peekEvent()")
    )
    override fun getValue(): EventWrapper<T>? {
        return super.getValue()
    }

    /**
     * 新建一个中间层. 用来筛掉那些已经被处理过的事件. 其实相当于注册一个 forever 的 observer
     */
    private fun createUnwrapMediator(): MediatorLiveData<T> {
        val mediator = MediatorLiveData<T>()
        mediator.addSource(this) { event ->
            if (!event.hasBeenHandled) {
                mediator.value = event.peekContent()
            }
        }
        return mediator
    }
}

open class MutablePublishData<T> : PublishData<T>() {
    public override fun publish(event: T) {
        super.publish(event)
    }
}