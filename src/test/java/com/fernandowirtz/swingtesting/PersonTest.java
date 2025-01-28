package com.fernandowirtz.swingtesting;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author gag
 */
public class PersonTest {

    @Test
    public void stringRepresentationShouldBeNameWithEmail() {
        Person instance = new Person("Gon", "gon@gmail.com");
        assertEquals("Gon (gon@gmail.com)", instance.toString());
    }
}
