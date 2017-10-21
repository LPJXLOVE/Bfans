package com.technology.lpjxlove.bfans;

import com.technology.lpjxlove.bfans.Util.NetRequestQueue;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by LPJXLOVE on 2017/1/18.
 */

public class UtilsTest {
    @Ignore
    @Test
    public void testNetRequestQueueHas(){
        boolean b=  NetRequestQueue.hasRequest(123);
        assertFalse(b);
    }


    @Test
    public void testNetRequestQueueNull(){

    }
}
