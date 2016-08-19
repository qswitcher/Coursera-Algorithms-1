/*
 * Copyright HomeAway, Inc 2005-2016. All Rights Reserved.
 * No unauthorized use of this software.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * Created by jrussom on 3/4/16.
 */
public class ChainingTest {

    @Test
    public void test() {
        String[] comboValuescombos = {
            "K I W V N C J",
            "K N V W I J C",
            "I C N V J K W",
            "W C N V J K I",
            "W N V I C K J"
        };

        String[] keys = new String[]{
            "C", "I", "J", "K", "N", "V", "W"
        };
        Integer[] values = new Integer[]{
            4, 3, 4, 5, 1, 2, 3
        };


        List<String> comboValues = new ArrayList<>();

        List<Integer> indexes = new ArrayList<>();
        indexes.addAll(Arrays.asList(0, 1, 2, 3, 4, 5, 6));
        for (List<Integer> list : generatePerm(indexes)) {
            LinearProbingHashST<String, Integer> map = new LinearProbingHashST<>(7);
            for (int index : list) {
                map.put(keys[index], values[index]);
            }
            comboValues.add(map.toString());
        }

        for (int i = 0; i < comboValuescombos.length; i++) {
            System.out.println(comboValues.contains(comboValuescombos[i]));
        }
    }

    public <E> List<List<E>> generatePerm(List<E> original) {
        if (original.size() == 0) {
            List<List<E>> result = new ArrayList<List<E>>();
            result.add(new ArrayList<E>());
            return result;
        }
        E firstElement = original.remove(0);
        List<List<E>> returnValue = new ArrayList<List<E>>();
        List<List<E>> permutations = generatePerm(original);
        for (List<E> smallerPermutated : permutations) {
            for (int index=0; index <= smallerPermutated.size(); index++) {
                List<E> temp = new ArrayList<E>(smallerPermutated);
                temp.add(index, firstElement);
                returnValue.add(temp);
            }
        }
        return returnValue;
    }
}
