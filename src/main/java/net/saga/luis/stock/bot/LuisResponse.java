/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.saga.luis.stock.bot;

import java.util.List;

/**
 *
 * @author summers
 */
public class LuisResponse {

    public String query;
    public Intent topScoringIntent;
    public List<Entity> entities;
    public SentimentAnalysis sentimentAnalysis;

    public static class Resolution {

        public String value, unit;
        public List<String> values;
    }

    public static class Entity {

        public String entity, type;
        public int startIndex, endEndex;
        public Double score;
        public Resolution resolution;

    }

    public static class SentimentAnalysis {

        public String label;
        public double score;
    }

    public static class Intent {

        public String intent;
        public double score;
    }

}
