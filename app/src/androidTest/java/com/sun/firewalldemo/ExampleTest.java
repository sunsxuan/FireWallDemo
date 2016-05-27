package com.sun.firewalldemo;

import android.test.InstrumentationTestCase;

/**
 * Created by S on 2016/5/15.
 */
public class ExampleTest extends InstrumentationTestCase {

    public void test() throws Exception{
        final int expected = 5;
        final int reality = 5;
        assertEquals(expected,reality);
    }



}
