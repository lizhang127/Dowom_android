package com.itcast.test;

import android.test.AndroidTestCase;

public class Testadd extends AndroidTestCase {
    public void testAdd(){
    	Add add = new Add();
    	int res = add.intadd(7, 11);
    	assertEquals(19, res);
    }
}
