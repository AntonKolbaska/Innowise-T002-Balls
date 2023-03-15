package com.music;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class IntervalsTest extends TestCase {


    //CONSTRUCTION TESTS
    @Test
    public void testIntervalConstructionByNull() {
        assertThrows(IllegalArgumentException.class, () -> Intervals.intervalConstruction(null));
    }

    @Test
    public void testIntervalConstructionWithIncorrectAmountOfElements() {
        String[] args = {"a", "b", "c", "g"};
        assertThrows(IllegalArgumentException.class, () -> Intervals.intervalConstruction(args));
    }

    @Test
    public void testIntervalConstructionWithInvalidInterval() {
        String[] args = {"F2", "G"};
        assertThrows(NullPointerException.class, () -> Intervals.intervalConstruction(args));
    }

    @Test
    public void testIntervalConstructionWithInvalidDegree() {
        String[] args = {"M2", "Y"};
        assertThrows(NullPointerException.class, () -> Intervals.intervalConstruction(args));
    }

    @Test
    public void testIntervalConstructionWithTwoElements() {
        List<String[]> list = List.of(new String[]{"M7", "G"}, new String[]{"P5", "B"},
                new String[]{"m2", "Fb"}, new String[]{"m2", "D#"});
        String[] expectedResult = {"F#", "F#", "Gbb", "E"};
        for (int i = 0; i < list.size(); i++) {
            assertEquals(expectedResult[i], Intervals.intervalConstruction(list.get(i)));
        }
    }

    @Test
    public void testIntervalConstructionWithThreeElementsAsc() {
        List<String[]> list = List.of(new String[]{"M7", "G", "asc"}, new String[]{"P5", "B", "asc"},
                new String[]{"m2", "Fb", "asc"}, new String[]{"m2", "D#", "asc"});
        String[] expectedResult = {"F#", "F#", "Gbb", "E"};
        for (int i = 0; i < list.size(); i++) {
            assertEquals(expectedResult[i], Intervals.intervalConstruction(list.get(i)));
        }
    }

    @Test
    public void testIntervalConstructionWithThreeElementsDsc() {
        List<String[]> list = List.of(new String[]{"P4", "E", "dsc"}, new String[]{"M3", "Cb", "dsc"},
                new String[]{"M2", "E#", "dsc"}, new String[]{"m3", "B", "dsc"});
        String[] expectedResult = {"B", "Abb", "D#", "G#"};
        for (int i = 0; i < list.size(); i++) {
            assertEquals(expectedResult[i], Intervals.intervalConstruction(list.get(i)));
        }
    }


    //IDENTIFICATION TESTS
    @Test
    public void testIntervalIdentificationByNull() {
        assertThrows(IllegalArgumentException.class, () -> Intervals.intervalIdentification(null));
    }

    @Test
    public void testIntervalIdentificationWithIncorrectAmountOfElements() {
        String[] args = {"a", "b", "c", "g"};
        assertThrows(IllegalArgumentException.class, () -> Intervals.intervalIdentification(args));
    }

    @Test
    public void testIntervalIdentificationWithInvalidDegree() {
        String[] args = {"M2", "Y"};
        assertThrows(NullPointerException.class, () -> Intervals.intervalIdentification(args));
    }

    @Test
    public void testIntervalIdentificationWithTwoElements() {
        List<String[]> list = List.of(new String[]{"C", "D"}, new String[]{"B", "F#"},
                new String[]{"Fb", "Gbb"}, new String[]{"G", "F#"});
        String[] expectedResult = {"M2", "P5", "m2", "M7"};
        for (int i = 0; i < list.size(); i++) {
            assertEquals(expectedResult[i], Intervals.intervalIdentification(list.get(i)));
        }
    }

    @Test
    public void testIntervalIdentificationWithThreeElementsAsc() {
        List<String[]> list = List.of(new String[]{"C", "D","asc"}, new String[]{"B", "F#","asc"},
                new String[]{"Fb", "Gbb","asc"}, new String[]{"G", "F#","asc"});
        String[] expectedResult = {"M2", "P5", "m2", "M7"};
        for (int i = 0; i < list.size(); i++) {
            assertEquals(expectedResult[i], Intervals.intervalIdentification(list.get(i)));
        }
    }

    @Test
    public void testIntervalIdentificationWithThreeElementsDsc() {
        List<String[]> list = List.of(new String[]{"Bb", "A", "dsc"}, new String[]{"Cb", "Abb", "dsc"},
                new String[]{"G#", "D#", "dsc"}, new String[]{"E", "B", "dsc"});
        String[] expectedResult = {"m2", "M3", "P4", "P4"};
        for (int i = 0; i < list.size(); i++) {
            assertEquals(expectedResult[i], Intervals.intervalIdentification(list.get(i)));
        }
    }
}