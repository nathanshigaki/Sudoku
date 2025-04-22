package org.sudoku.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotifierService {

    private final Map<EventEnum, List<EventListener>> listeners = new HashMap<>(){{
        put(EventEnum.CLEAR_SPACE, new ArrayList<>());
    }};

    public void subscribe(final EventEnum eventEnum, EventListener listener){
        var selectedListener = listeners.get(eventEnum);
        selectedListener.add(listener);
    }

    public void notify(final EventEnum eventEnum){
        listeners.get(eventEnum).forEach(l -> l.update(eventEnum));
    }
}
