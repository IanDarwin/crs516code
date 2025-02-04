package com.ltree.crs516;

import java.util.Random;
import java.util.function.Supplier;

/**
 * Randomly selects a rank.
 * @return
 */
public class RankSupplier implements Supplier<Rank> {

    private static final Random random = new Random();

    @Override
    public Rank get() {
        return Rank.values()[random.nextInt(Rank.MAX)];
    }
}
