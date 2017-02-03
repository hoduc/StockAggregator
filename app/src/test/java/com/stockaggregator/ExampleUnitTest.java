package com.stockaggregator;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {

    Pattern pattern = Pattern.compile("[\\d]{2}[PT]");
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test() throws Exception{
        Matcher m = pattern.matcher("30C");
        //System.out.println(m.find());
        assertTrue(m.find());
    }
}