package Main;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class ScoreManager { // create a .txt to log all users high scores
    private static final String FILE_NAME = "highscores.txt";
    private static final int MAX_SCORES = 5;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public static class ScoreEntry {
        int score;
        String date;

        public ScoreEntry(int score, String date) {
            this.score = score;
            this.date = date;
        }

        @Override
        public String toString() {
            return score + "," + date;
        }

        public static ScoreEntry fromString(String line) {
            String[] parts = line.split(",");
            return new ScoreEntry(Integer.parseInt(parts[0]), parts[1]);
        }
    }

    public static void addScore(int score) {
        List<ScoreEntry> scores = loadScores();
        scores.add(new ScoreEntry(score, DATE_FORMAT.format(new Date())));
        scores.sort((a, b) -> b.score - a.score); // descending
        if (scores.size() > MAX_SCORES) {
            scores = scores.subList(0, MAX_SCORES);
        }
        saveScores(scores);
    }

    public static List<ScoreEntry> loadScores() {
        List<ScoreEntry> scores = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) return scores;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                scores.add(ScoreEntry.fromString(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scores;
    }

    public static void saveScores(List<ScoreEntry> scores) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (ScoreEntry entry : scores) {
                bw.write(entry.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printScores() {
        List<ScoreEntry> scores = loadScores();
        System.out.println("Top 5 High Scores:");
        int rank = 1;
        for (ScoreEntry entry : scores) {
            System.out.println("Rank " + rank + ": " + entry.score + " pts on " + entry.date);
            rank++;
        }
    }
}
