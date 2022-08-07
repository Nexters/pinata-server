package com.nexters.pinataserver.event.domain;

import com.nexters.pinataserver.common.util.RandomNumberGenerator;

public enum EventType {

    FCFS {
        @Override
        public boolean isHit(Event event) {
            return event.isPossibleHit();
        }
    }, RANDOM {
        @Override
        public boolean isHit(Event event) {
            int randomNumber = RandomNumberGenerator.getRandomNumber(BOUND);

            return event.isPossibleHit() && (randomNumber <= HIT_PERCENTAGE);
        }
    },
    ;

    private static final int HIT_PERCENTAGE = 30;

    private static final int BOUND = 100;

    public boolean isFCFS() {
        return this == FCFS;
    }

    public boolean isRANDOM() {
        return this == RANDOM;
    }

    public abstract boolean isHit(Event event);

}
