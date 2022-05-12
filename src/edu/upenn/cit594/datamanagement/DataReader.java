package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.util.StateData;
import edu.upenn.cit594.util.Tweet;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DataReader {

    String fileName;
    String stateFile;
    public DataReader(String fileName, String stateFile) {
        this.fileName = fileName;
        this.stateFile = stateFile;
    }

    public List<Tweet> getData() {
        String extension = findExtension();
        if (extension.equals("")) {return null;}
        if (extension.equals(".json")) {
        return jsonParse();
        } else {
        return textParse();
        }
    }

    private String findExtension() {
        int index = fileName.lastIndexOf(".");
        if (index == -1) { return "";}
        return fileName.substring(index);
    }

    private List<Tweet> textParse() {
        List<Tweet> tweets = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            while((line = br.readLine()) != null) {
                String[] tokens = line.split("\t");
                String[] coordinates = tokens[0].split(",");
                double latitude = Double.parseDouble(coordinates[0].substring(1));
                double longitude = Double.parseDouble(coordinates[1].substring(1,coordinates[1].length() - 1));//substring ends before square bracket "]"
                String tweet = tokens[3];
                tweets.add(new Tweet(latitude,longitude,tweet));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tweets;
    }
    private List<Tweet> jsonParse() {
        List<Tweet> tweets = new ArrayList<>();
        try {
            Object obj = new JSONParser().parse(new FileReader(fileName));
            JSONArray ja = (JSONArray) obj;
            int length = ja.size();
            for (Object o : ja) {
                JSONObject jo = (JSONObject) o;
                JSONArray locations = (JSONArray) jo.get("location");
                Double latitude = (Double) locations.get(0);
                Double longitude = (Double) locations.get(1);
                String tweet = (String) jo.get("text");
                tweets.add(new Tweet(latitude, longitude, tweet));
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return tweets;
        //return null;//TODO: MUST FIND OUT HOW TO EVEN PROCESS JSON
    }
    public List<StateData> stateParse() {
        List<StateData> states = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(stateFile));
            String line;
            while((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                String state = tokens[0];
                double latitude = Double.parseDouble(tokens[1]);
                double longitude = Double.parseDouble(tokens[2]);
                StateData thisState = new StateData(state,latitude,longitude);
                states.add(thisState);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return states;
    }
}
