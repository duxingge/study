package com.wjx.concurrency.bookTemplate.unit1.kit;

/**
 * @Author wangjiaxing
 * @Date 2023/2/16
 */
public class EventSource {
    EventListener eventListener;

    public EventListener getEventListener() {
        return eventListener;
    }

    public void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }
}
