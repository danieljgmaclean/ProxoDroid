package com.danieljgmaclean.proxodroid.events;

import com.squareup.otto.Bus;

/**
 * Created by danieljgmaclean on 22/03/15.
 */
public class MyBus {

    private static final Bus BUS = new Bus();

    public static Bus getInstance() {
        return BUS;
    }
}