package com.nexters.pinataserver.common.util;

import java.util.Random;

public class RandomNumberGenerator {

    public static final Random random = new Random();

    public static Integer getRandomNumber(Integer bound) {
        return random.nextInt(bound);
    }

}
