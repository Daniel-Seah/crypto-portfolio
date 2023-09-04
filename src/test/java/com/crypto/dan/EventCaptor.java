package com.crypto.dan;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class EventCaptor<T> implements Observer {
    private final List<T> events;

    public EventCaptor() {
        this.events = new ArrayList<>();
    }

    public List<T> getEvents() {
        return events;
    }

    @Override
    public void update(Observable o, Object arg) {
        events.add((T) arg);
    }
}
