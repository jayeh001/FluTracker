package edu.upenn.cit594.ui;

import edu.upenn.cit594.processor.Processor;

import java.util.*;

public class UI {
    protected Processor processor;

    public UI(Processor processor) {
        this.processor = processor;
    }

    public void printFluTweets() {
        Map<String,Integer> fluMap = processor.findState();
        Set<String> stateSet = fluMap.keySet();
        List<String> stateList = new ArrayList<>(stateSet);
        Collections.sort(stateList);
        for (String state: stateList) {
            String count = Integer.toString(fluMap.get(state));
            System.out.println(state + ": " + count);
        }
    }
}
