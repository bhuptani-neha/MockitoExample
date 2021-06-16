package org.example;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.stub;

public class SpyListTest {

    @Test
    public void createSpyOnArrayList(){
        List<String> listspy = spy(ArrayList.class);
        listspy.add("Test");
        listspy.add("123");

        assertEquals(2, listspy.size());
        assertEquals("Test",listspy.get(0));
    }

    @Test
    public void createSpyOnArrayList_overridingSpecificMethods(){
        List<String> listspy = spy(ArrayList.class);
        listspy.add("Test");
        listspy.add("123");

        stub(listspy.size()).toReturn(-1);

        assertEquals(-1, listspy.size());
        assertEquals("Test",listspy.get(0));
    }
}
