package com.music;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class Intervals {
    public static final String SEMITONE = "semitones";
    public static final String DEGREE = "degrees";

    //K - String value
    //V - map of <K - semitone, V - degree>
    private static final Map<String, Map<String, Integer>> interval = new HashMap<>();

    //accidentals
    private static Map<String, Integer> semitonesMap = new HashMap<>();

    private static final Map<String, Integer> originalSemitonesMap = new HashMap<>();
    private static final Map<String, Integer> reverseSemitonesMap = new HashMap<>();

    private static final String[] noteNameOrder = {"A", "B", "C", "D", "E", "F", "G"};

    private static Boolean isReversed = false;

    static {
        //Intervals names, quality, and quantity
        interval.put("m2", Map.of(SEMITONE, 1, DEGREE, 2));
        interval.put("M2", Map.of(SEMITONE, 2, DEGREE, 2));
        interval.put("m3", Map.of(SEMITONE, 3, DEGREE, 3));
        interval.put("M3", Map.of(SEMITONE, 4, DEGREE, 3));
        interval.put("P4", Map.of(SEMITONE, 5, DEGREE, 4));
        interval.put("P5", Map.of(SEMITONE, 7, DEGREE, 5));
        interval.put("m6", Map.of(SEMITONE, 8, DEGREE, 6));
        interval.put("M6", Map.of(SEMITONE, 9, DEGREE, 6));
        interval.put("m7", Map.of(SEMITONE, 10, DEGREE, 7));
        interval.put("M7", Map.of(SEMITONE, 11, DEGREE, 7));
        interval.put("P8", Map.of(SEMITONE, 12, DEGREE, 8));

        semitonesMap.put("b", 1);
        semitonesMap.put("bb", 2);
        semitonesMap.put("", 0);
        semitonesMap.put("#", -1);
        semitonesMap.put("##", -2);

        originalSemitonesMap.put("b", 1);
        originalSemitonesMap.put("bb", 2);
        originalSemitonesMap.put("", 0);
        originalSemitonesMap.put("#", -1);
        originalSemitonesMap.put("##", -2);

        reverseSemitonesMap.put("b", -1);
        reverseSemitonesMap.put("bb", -2);
        reverseSemitonesMap.put("", 0);
        reverseSemitonesMap.put("#", 1);
        reverseSemitonesMap.put("##", 2);
    }


    //Args validation check with intervalConstrunction exception
    public static void constructionPrecondition(String[] args) {
        if (args == null) {
            throw new IllegalArgumentException("Illegal number of elements in input array");
        }
        if (args.length != 3 && args.length != 2) {
            throw new IllegalArgumentException("Illegal number of elements in input array");
        }
    }

    //Args validation check with intervalIdentification exception
    public static void identificationPrecondition(String[] args) {
        if (args == null) {
            throw new IllegalArgumentException("Cannot identify the interval");
        }
        if (args.length != 3 && args.length != 2) {
            throw new IllegalArgumentException("Cannot identify the interval");
        }
    }



    //this function gets semitone, separating symbols after note, if existing
    public static String getSemitoneValue(String semitone) {
        String semitoneValue = "";
        if (semitone.length() > 2) {
            semitoneValue = semitone.substring(1, 3);
        } else if (semitone.length() > 1) {
            semitoneValue = semitone.substring(1, 2);
        }

        return semitoneValue;
    }

    //this function counts amount of semitones between start and result degree
    public static int countSemitone(int startDegree, int endDegree, int startSemitone, int semitoneToMove) {
        int semitoneAmount = 0;
        int fullCircle = 7;

        //which gaps consists only of 1 semitone
        List<Integer> gapWithOneSemitone = List.of(1, 4, 9, 11);

        if (startDegree >= endDegree) {
            endDegree += fullCircle;
        }

        for (int i = startDegree; i < endDegree; i++) {
            if (gapWithOneSemitone.contains(i)) {
                semitoneAmount++;
            } else {
                semitoneAmount += 2;
            }
        }

        return semitoneAmount - semitoneToMove + startSemitone;
    }



    //check if semitone correct and get its value
    public static int getSemitoneEntry(String value){
        if(semitonesMap.containsKey(value)){
            return semitonesMap.get(value);
        }
        else{
            throw new NullPointerException("Invalid semitone");
        }
    }

    //check if semitone correct and get its value
    public static int getDegreeEntry(String note){
        int DegreePosition = Arrays.binarySearch(noteNameOrder, note);
        if (DegreePosition < 0) {
            throw new NullPointerException("Invalid degree");
        }
        else{
            return DegreePosition;
        }
    }



    public static String intervalConstruction(String[] args) {

        //args validation check
        constructionPrecondition(args);


        //args[1] is string that contains two parts(second is optional)
        // first is note,
        //second is semitone
        // in below code lines we divide our string into this two parts
        String startNote = args[1].substring(0, 1);
        String semitone = getSemitoneValue(args[1]);


        int startDegreePosition = getDegreeEntry(startNote);

        //validation check for semitone
        int startSemitone = getSemitoneEntry(semitone);


        //using our interval we can count how many degrees and semitones to move
        int degreeGap = interval.get(args[0]).get(DEGREE) - 1;
        int semitoneGap = interval.get(args[0]).get(SEMITONE);


        //if reversed - modify
        if (args.length == 3 && args[2].equals("dsc")) {
            isReversed = true;
            Collections.reverse(Arrays.asList(noteNameOrder));
            semitonesMap = reverseSemitonesMap;
            startDegreePosition = 6 - startDegreePosition;
            startSemitone = -startSemitone;
        }

        //to found result degree we need to move from the start degree taking into account
        //that degrees works like cycle with period of 7 elements
        int endDegreePosition = startDegreePosition + degreeGap;

        if (endDegreePosition >= 7) {
            endDegreePosition -= 7;
        }

        String endDegree = noteNameOrder[endDegreePosition];

        //get resultin' semitone
        int endSemitoneIndex = countSemitone(startDegreePosition, endDegreePosition, startSemitone, semitoneGap);

        String endSemitone = semitonesMap.entrySet()
                .stream()
                .filter(entry -> entry.getValue() == endSemitoneIndex)
                .map(Entry::getKey).collect(Collectors.joining());


        //reset collections to default conditions if was reversed
        if (isReversed == true) {
            Collections.reverse(Arrays.asList(noteNameOrder));
            semitonesMap = originalSemitonesMap;
            isReversed = false;
        }

        return endDegree + endSemitone;
    }

    public static String intervalIdentification(String[] args) {

        //args validation check
        identificationPrecondition(args);

        String endNoteValue = args[1].substring(0, 1);
        String endSemitoneValue = getSemitoneValue(args[1]);

        //if order is reversed we can just call function reversing args
        if (args.length == 3 && args[2].equals("dsc")) {
            return intervalIdentification(new String[]{args[1], args[0]});
        }

        //args[0] and args[1] are strings that contains two parts(second is optional)
        //first is note,
        //second is semitone
        // in below code lines we divide our string into this two parts
        String startNoteValue = args[0].substring(0, 1);
        String startSemitoneValue = getSemitoneValue(args[0]);

        //validation check for starting degree
        int startDegreePosition = getDegreeEntry(startNoteValue);

        //validation check for semitone
        int startSemitone = getSemitoneEntry(startSemitoneValue);

        //validation check for ending degree
        int endDegreePosition = getDegreeEntry(endNoteValue);

        //validation check for semitone
        int endSemitonePosition = getSemitoneEntry(endSemitoneValue);


        //With knowledge about start and end both of degree and semitone we can calculate how many
        //degrees and semitones are between them
        if (endDegreePosition <= startDegreePosition) {
            startDegreePosition -= 7;
        }
        int degreeToMove = endDegreePosition - startDegreePosition + 1;
        int semitoneToMove = countSemitone(startDegreePosition, endDegreePosition, startSemitone, endSemitonePosition);

        //With knowledge about amount of degrees and semitones between start and end
        //we can find the interval
        Map<String, Integer> resultMap = Map.of(SEMITONE, semitoneToMove, DEGREE, degreeToMove);
        String resultInterval = interval.entrySet()
                .stream()
                .filter(entry -> entry.getValue().equals(resultMap))
                .map(Entry::getKey).collect(Collectors.joining());

        return resultInterval;
    }


}
