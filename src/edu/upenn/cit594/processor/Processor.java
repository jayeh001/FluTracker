package edu.upenn.cit594.processor;

import edu.upenn.cit594.datamanagement.DataReader;
import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.util.StateData;
import edu.upenn.cit594.util.Tweet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Processor {
    protected DataReader dataReader;
    protected List<Tweet> tweets;
    protected List<StateData> states;
    public Processor(DataReader dataReader) {
       this.dataReader = dataReader;
       tweets = dataReader.getData();
       states = dataReader.stateParse();
    }
    public List<Tweet> findFluTweets() {
        Pattern p = Pattern.compile("(?<![^#\\s])flu(?![a-z])", Pattern.CASE_INSENSITIVE);
        List<Tweet> newTweets = new ArrayList<>();
        for (Tweet tweet: tweets){
            String statement = tweet.getTweet();
            Matcher m = p.matcher(statement);
            boolean isFound = m.find();
            if (isFound) {
                newTweets.add(tweet);
            }
        }
        return newTweets;
    }
    public Map<String,Integer> findState() {
        List<Tweet> validTweets = findFluTweets();
        Map<String, Integer> tweetsByState = new HashMap<>();
        for (Tweet tweet: validTweets) {
            double min = Double.MAX_VALUE;
            String minState = "";
            double latitude = tweet.getLatitude();
            double longitude = tweet.getLongitude();
            for (int i = 0; i < states.size(); i++) {
                double distance = getDistance(latitude,longitude,states.get(i));
                double prevMin = min;
                if ((min = Math.min(min,distance)) != prevMin) {
                    minState = states.get(i).getState();
                }
            }
            tweetsByState.merge(minState, 1, (a, b) -> Integer.sum(a, b));
            Logger.getInstance().logEvent(minState,tweet.getTweet());
        }
        return tweetsByState;
    }
    private double getDistance(double latitude,double longitude, StateData state) {
        double stateLatitude = state.getLatitude();
        double stateLongitude = state.getLongitude();
        double finalLongitude = Math.pow(longitude - stateLongitude, 2);
        double finalLatitude = Math.pow(latitude - stateLatitude, 2);
        return Math.sqrt(finalLongitude + finalLatitude);
    }
    // (?<![^#\s])flu(?![a-z])
}