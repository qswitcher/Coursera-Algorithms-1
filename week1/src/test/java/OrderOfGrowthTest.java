/*
 * Copyright HomeAway, Inc 2005-2016. All Rights Reserved.
 * No unauthorized use of this software.
 */

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Created by jrussom on 2/6/16.
 */
public class OrderOfGrowthTest {

    @Test
    public void orderOfGrowth() {
        List<BigInteger> times = new ArrayList<BigInteger>();

        for (int n = 2; n < 100; n++) {
            BigInteger N = BigInteger.valueOf(2).pow(n);

            BigInteger sum = BigInteger.ZERO;
            BigInteger end = N.multiply(N);
            for (BigInteger i = BigInteger.ONE; i.compareTo(end) <= 0 ; i = i.multiply(BigInteger.valueOf(2))) {
                sum = sum.add(BigInteger.ONE);
            }

            times.add(sum);
        }

        StringBuilder sb = new StringBuilder();

        for (BigInteger value : times) {
            sb.append(value.toString());
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }
}
