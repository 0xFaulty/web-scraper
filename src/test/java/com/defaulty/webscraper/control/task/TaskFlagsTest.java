package com.defaulty.webscraper.control.task;

import org.junit.Test;

import static org.junit.Assert.*;

public class TaskFlagsTest {

    @Test
    public void containsTest() {
        TaskFlags flags = TaskFlags.V;
        assertTrue(flags.containsIn(TaskFlags.V.getValue()));
        flags = TaskFlags.W;
        assertTrue(flags.containsIn(TaskFlags.W.getValue()));
        flags = TaskFlags.C;
        assertTrue(flags.containsIn(TaskFlags.C.getValue()));
        flags = TaskFlags.E;
        assertTrue(flags.containsIn(TaskFlags.E.getValue()));

        assertFalse(flags.containsIn(TaskFlags.V.getValue()));
        assertFalse(flags.containsIn(TaskFlags.W.getValue()));
        assertFalse(flags.containsIn(TaskFlags.C.getValue()));
    }

    @Test
    public void containsAllTest() throws Exception {
        int flags = TaskFlags.V.getValue();
        flags |= TaskFlags.W.getValue();
        flags |= TaskFlags.C.getValue();
        flags |= TaskFlags.E.getValue();

        assertTrue(TaskFlags.V.containsIn(flags));
        assertTrue(TaskFlags.W.containsIn(flags));
        assertTrue(TaskFlags.C.containsIn(flags));
        assertTrue(TaskFlags.E.containsIn(flags));
    }

}