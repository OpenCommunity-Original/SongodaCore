package com.songoda.core.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MathUtilsTest {
    private final String warningMessage = "MathTest";
    private final String warningMessageExpectedStart = warningMessage + " ";

    @Test
    void eval() {
        assertEquals(10.5 + 4, MathUtils.eval("10.5 + 4"));
        assertEquals(10.5 - 5, MathUtils.eval("10.5 - 5"));
        assertEquals(10 * 4, MathUtils.eval("10 * 4"));
        assertEquals(10.5 / .5, MathUtils.eval("10.5 / .5"));

        assertEquals(20 - +4, MathUtils.eval("20 - +4"));
        assertEquals(Math.pow(10, 2), MathUtils.eval("10^2"));
        assertEquals(Math.pow(-10, 2), MathUtils.eval("(-10)^2"));
        assertEquals(-Math.pow(10, 2), MathUtils.eval("-10^2"));

        assertEquals(Math.sqrt(49), MathUtils.eval("sqrt(49)"));
        assertEquals(Math.sin(Math.toRadians(90)), MathUtils.eval("sin(90)"));
        assertEquals(Math.cos(Math.toRadians(90)), MathUtils.eval("cos(90)"));
        assertEquals(Math.tan(Math.toRadians(89650)), MathUtils.eval("tan(89650)"));

        assertEquals(Integer.MAX_VALUE + (long) Integer.MAX_VALUE,
                MathUtils.eval(Integer.MAX_VALUE + "+" + Integer.MAX_VALUE));
    }

    @Test
    void evalWithCommaAsDecimalSeparator() {
        Exception ex = assertThrowsExactly(RuntimeException.class, () -> MathUtils.eval("1,0", warningMessage));

        assertTrue(ex.getMessage().startsWith(warningMessageExpectedStart),
                () -> "'" + ex.getMessage() + "' does not start with '" + warningMessageExpectedStart + "'");
        assertTrue(ex.getMessage().contains("Unexpected: "));
    }

    @Test
    void evalWithUnsupportedSyntax() {
        Exception ex = assertThrowsExactly(RuntimeException.class, () -> MathUtils.eval("!2", warningMessage));

        assertTrue(ex.getMessage().startsWith(warningMessageExpectedStart),
                () -> "'" + ex.getMessage() + "' does not start with '" + warningMessageExpectedStart + "'");
        assertTrue(ex.getMessage().contains("Unexpected: "));
    }

    @Test
    void evalWithUnsupportedFunction() {
        Exception ex = assertThrowsExactly(RuntimeException.class, () -> MathUtils.eval("acos(90)", warningMessage));

        assertTrue(ex.getMessage().startsWith(warningMessageExpectedStart),
                () -> "'" + ex.getMessage() + "' does not start with '" + warningMessageExpectedStart + "'");
        assertTrue(ex.getMessage().contains("Unknown function: "));
    }
}
