package challenges.day2;

import challenges.util.FileLineReader;
import java.util.List;
import static challenges.day2.ChallengeDay2.Colours.*;

public class ChallengeDay2 {

    public static void main(String[] args) {
        var challengeDay2 = new ChallengeDay2();
        var games = new FileLineReader().getFileLines("inputDay2.txt");
        System.out.println("The sum of all possible games is: " + challengeDay2.findSolution(games));
    }

    public Integer findSolution(List<String> games) {
        var sumOfPossibleGames = 0;
        for (var game : games) {
            var gameComponents = game.split(":"); // ["Game 1"]["1 blue; 2 red, 6 green"]
            var gameIdentifier = Integer.valueOf(gameComponents[0].substring(5)); // "1"
            var handfuls = gameComponents[1].split(";"); // ["1 blue"] ["2 red, 6 green"]

           if (isValidHandful(handfuls)) {
                sumOfPossibleGames += gameIdentifier;
            }
        }

        return sumOfPossibleGames;
    }

    private static boolean isValidHandful(String[] handfuls) {
        var isValid = false;

        for (var handful : handfuls) {
            var cubes = handful.split(","); // ["2 red"]  ["6 green"]
            isValid = areAllCubesValid(cubes);
            if (!isValid) {
                break;
            }
        }
        return isValid;
    }

    private static boolean areAllCubesValid(String[] cubes) {
        boolean isValid = false;

        for (var cube : cubes) {
            var cubeDetails = cube.trim().split(" ");
            var cubeAmount = cubeDetails[0];
            var cubeColour = Colours.valueOf(cubeDetails[1].toUpperCase());

            switch (cubeColour) {
                case BLUE:
                    isValid = Integer.valueOf(cubeAmount) <= BLUE.maxValue;
                    break;
                case RED:
                    isValid = Integer.valueOf(cubeAmount) <= RED.maxValue;
                    break;
                case GREEN:
                    isValid = Integer.valueOf(cubeAmount) <= GREEN.maxValue;
                    break;
            }

            if (!isValid) {
                break;
            }
        }
        return isValid;
    }

    enum Colours {
        BLUE(14),
        RED(12),
        GREEN(13);

        final int maxValue;

        Colours(int maxValue) {
            this.maxValue = maxValue;
        }
    }
}
