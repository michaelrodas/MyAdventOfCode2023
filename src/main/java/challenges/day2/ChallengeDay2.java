package challenges.day2;

import challenges.util.FileLineReader;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

import static challenges.day2.ChallengeDay2.Colours.*;

public class ChallengeDay2 {

    public static void main(String[] args) {
        var challengeDay2 = new ChallengeDay2();
        var games = FileLineReader.getFileLines("inputDay2.txt");
        Logger.getAnonymousLogger().info("The sum of all possible games is: " + challengeDay2.findSolution2(games));
    }

    private int findSolution2(List<String> games) {
        var sumOfPossibleGames = 0;
        for (var game : games) {
            var gameComponents = game.split(":"); // ["Game 1"]["1 blue; 2 red, 6 green"]
            var handfuls = gameComponents[1].split(";"); // ["1 blue"] ["2 red, 6 green"]

            sumOfPossibleGames += getPowerOfGame(handfuls);
        }

        return sumOfPossibleGames;
    }

    private int getPowerOfGame(String[] handfuls) {
        var blueCubes = new ArrayList<Integer>();
        var redCubes = new ArrayList<Integer>();
        var greenCubes = new ArrayList<Integer>();

        for (var handful : handfuls) {
            var cubes = handful.split(","); // ["2 red"]  ["6 green"]
            for (var cube : cubes) {
                var cubeDetails = cube.trim().split(" ");
                var cubeAmount = cubeDetails[0];
                var cubeColour = Colours.valueOf(cubeDetails[1].toUpperCase());

                switch (cubeColour) {
                    case BLUE -> blueCubes.add(Integer.valueOf(cubeAmount));
                    case RED -> redCubes.add(Integer.valueOf(cubeAmount));
                    case GREEN -> greenCubes.add(Integer.valueOf(cubeAmount));
                }
            }
        }

        var maxBlue = blueCubes.stream().max(Comparator.naturalOrder());
        //Another possibility for obtaining max...
//        blueCubes.sort(Comparator.naturalOrder());
//        blueCubes.get(blueCubes.size()-1);
        var maxRed = redCubes.stream().max(Comparator.naturalOrder());
        var maxGreen = greenCubes.stream().max(Comparator.naturalOrder());

        return maxBlue.orElse(1) * maxRed.orElse(1) * maxGreen.orElse(1);
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

            isValid = switch (cubeColour) {
                case BLUE -> Integer.parseInt(cubeAmount) <= BLUE.maxValue;
                case RED -> Integer.parseInt(cubeAmount) <= RED.maxValue;
                case GREEN -> Integer.parseInt(cubeAmount) <= GREEN.maxValue;
            };

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

/**
 * Challenge 1
 *
 * As you walk, the Elf shows you a small bag and some cubes which are either red, green, or blue. Each time you play this game, he will hide a secret number of cubes of each color in the bag, and your goal is to figure out information about the number of cubes.
 *
 * To get information, once a bag has been loaded with cubes, the Elf will reach into the bag, grab a handful of random cubes, show them to you, and then put them back in the bag. He'll do this a few times per game.
 *
 * You play several games and record the information from each game (your puzzle input). Each game is listed with its ID number (like the 11 in Game 11: ...) followed by a semicolon-separated list of subsets of cubes that were revealed from the bag (like 3 red, 5 green, 4 blue).
 *
 * For example, the record of a few games might look like this:
 *
 * Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
 * Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
 * Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
 * Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
 * Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
 *
 * In game 1, three sets of cubes are revealed from the bag (and then put back again). The first set is 3 blue cubes and 4 red cubes; the second set is 1 red cube, 2 green cubes, and 6 blue cubes; the third set is only 2 green cubes.
 *
 * The Elf would first like to know which games would have been possible if the bag contained only 12 red cubes, 13 green cubes, and 14 blue cubes?
 *
 * In the example above, games 1, 2, and 5 would have been possible if the bag had been loaded with that configuration. However, game 3 would have been impossible because at one point the Elf showed you 20 red cubes at once; similarly, game 4 would also have been impossible because the Elf showed you 15 blue cubes at once. If you add up the IDs of the games that would have been possible, you get 8.
 *
 * Determine which games would have been possible if the bag had been loaded with only 12 red cubes, 13 green cubes, and 14 blue cubes. What is the sum of the IDs of those games?
 *
 *
 * ----------------
 *
 * Challenge 2
 *
 * The Elf says they've stopped producing snow because they aren't getting any water! He isn't sure why the water stopped; however, he can show you how to get to the water source to check it out for yourself. It's just up ahead!
 *
 * As you continue your walk, the Elf poses a second question: in each game you played, what is the fewest number of cubes of each color that could have been in the bag to make the game possible?
 *
 * Again consider the example games from earlier:
 *
 * Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
 * Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
 * Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
 * Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
 * Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
 * In game 1, the game could have been played with as few as 4 red, 2 green, and 6 blue cubes. If any color had even one fewer cube, the game would have been impossible.
 * Game 2 could have been played with a minimum of 1 red, 3 green, and 4 blue cubes.
 * Game 3 must have been played with at least 20 red, 13 green, and 6 blue cubes.
 * Game 4 required at least 14 red, 3 green, and 15 blue cubes.
 * Game 5 needed no fewer than 6 red, 3 green, and 2 blue cubes in the bag.
 * The power of a set of cubes is equal to the numbers of red, green, and blue cubes multiplied together. The power of the minimum set of cubes in game 1 is 48. In games 2-5 it was 12, 1560, 630, and 36, respectively. Adding up these five powers produces the sum 2286.
 *
 * For each game, find the minimum set of cubes that must have been present. What is the sum of the power of these sets?
 *
 */
