package com.ltree.crs516;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

public class RankSupplierTest {

    private RankSupplier rankSupplier;

    @BeforeEach
    void init() {
        rankSupplier = new RankSupplier();
    }

    // Just create a bunch to make sure "nothing can go wrong."
    @Test
    public void testRandomStuff() {
        IntStream.of(0,100000).forEach(i -> rankSupplier.get());
    }
}
