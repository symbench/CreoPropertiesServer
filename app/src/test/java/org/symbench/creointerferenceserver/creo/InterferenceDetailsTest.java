package org.symbench.creointerferenceserver.creo;

import org.junit.Test;
import org.symbench.creointerferenceserver.http.InterferenceDetails;

import static org.junit.Assert.*;

public class InterferenceDetailsTest {
    @Test public void interferenceDetailsHasTwoParts() {
        InterferenceDetails details = new InterferenceDetails(
                "para1.prt",
                "para2.prt",
                14.000
        );
        assertEquals(details.getPart1Name(), "para1.prt");
        assertEquals(details.getPart2Name(), "para2.prt");
    }
}
