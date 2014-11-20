package com.company.app;

import org.junit.Assert;
import org.junit.Test;
import com.company.app.Core;
import static org.junit.Assert.*;

public class CoreTest {

    @Test
    public void testSum() throws Exception {
        Assert.assertFalse(Boolean.FALSE);
        Assert.assertTrue(new Core().sum(2,2)==5);
        Assert.assertTrue(new Core().sum(2,2)==4);

    }
}