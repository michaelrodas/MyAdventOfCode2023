package challenges;

import challenges.util.FileLineReader;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class ChallengeDay5 {

    public static void main(String[] args) {
        var input = FileLineReader.getFileLines("inputDay5.txt");

        var seeds = getAsLongList(input.get(0).substring(7));

        var data = new HashMap<String, List<List<Long>>>();

        var iterator = input.iterator();
        var results = new ArrayList<Long>();
        AtomicLong result = new AtomicLong(0L);
        while (iterator.hasNext()) {
            var line = iterator.next();

            if (line.equals("seed-to-soil map:") || line.equals("soil-to-fertilizer map:") ||
                    line.equals("fertilizer-to-water map:") || line.equals("water-to-light map:") ||
                    line.equals("light-to-temperature map:") || line.equals("temperature-to-humidity map:") ||
                    line.equals("humidity-to-location map:")) {
                data.put(line, extractedValues(iterator));
            }
        }

        seeds.forEach( seed -> { //seed 79
            var seedToSoils = data.get("seed-to-soil map:"); //get(1) =  [52, 50, 48]

            AtomicBoolean foundResult = new AtomicBoolean(false);

            seedToSoils.forEach( numbers -> {

                Long source = numbers.get(1);
                Long destination = numbers.get(0);
                Long rangeLength = numbers.get(2);
                var maxSource = source + rangeLength;

                if (seed >= source && seed <= maxSource){
                    var sourceLocation = seed - source; // 79 - 50  = 29
                    var destinationLocation = sourceLocation + destination; // 29+52 = 81
                    var maxDestination = destination + rangeLength;// 52 + 48 = 100
                    if(destinationLocation < maxDestination) {
                        results.add(destinationLocation);
                        foundResult.set(true);
                        result.set(destinationLocation);
                    }
                }

            });
            if (!foundResult.get()){
                results.add(seed);
                result.set(seed);
            }//[81, 14, 57, 13]

//                var destinationRange = LongStream.rangeClosed(seedToSoils.get(0).get(0), seedToSoils.get(0).get(2)).toList();
        });

        System.out.println("Finished: " + results);
    }

    @NotNull
    private static List<Long> getAsLongList(String input) {
        return Arrays.stream(input.split(" ")).map(Long::parseLong).toList();
    }

    private static List<List<Long>> extractedValues(Iterator<String> iterator) {
        var values = new ArrayList<List<Long>>();
        String line;
        line = iterator.next();
        while (!line.isEmpty()) {
            values.add(Arrays.stream(line.split(" ")).map(Long::parseLong).toList());
            if (iterator.hasNext()) {
                line = iterator.next();
            } else {
                break;
            }
        }
        return values;
    }
}


/**
 * The almanac (your puzzle input) lists all of the seeds that need to be planted. It also lists what type of soil to use with each kind of seed, what type of fertilizer to use with each kind of soil, what type of water to use with each kind of fertilizer, and so on. Every type of seed, soil, fertilizer and so on is identified with a number, but numbers are reused by each category - that is, soil 123 and fertilizer 123 aren't necessarily related to each other.
 *
 * For example:
 *
 * seeds: 79 14 55 13
 *
 * seed-to-soil map:
 * 50 98 2
 * 52 50 48
 *
 * soil-to-fertilizer map:
 * 0 15 37
 * 37 52 2
 * 39 0 15
 *
 * fertilizer-to-water map:
 * 49 53 8
 * 0 11 42
 * 42 0 7
 * 57 7 4
 *
 * water-to-light map:
 * 88 18 7
 * 18 25 70
 *
 * light-to-temperature map:
 * 45 77 23
 * 81 45 19
 * 68 64 13
 *
 * temperature-to-humidity map:
 * 0 69 1
 * 1 0 69
 *
 * humidity-to-location map:
 * 60 56 37
 * 56 93 4
 * The almanac starts by listing which seeds need to be planted: seeds 79, 14, 55, and 13.
 *
 * The rest of the almanac contains a list of maps which describe how to convert numbers from a source category into numbers in a destination category. That is, the section that starts with seed-to-soil map: describes how to convert a seed number (the source) to a soil number (the destination). This lets the gardener and his team know which soil to use with which seeds, which water to use with which fertilizer, and so on.
 *
 * Rather than list every source number and its corresponding destination number one by one, the maps describe entire ranges of numbers that can be converted. Each line within a map contains three numbers: the destination range start, the source range start, and the range length.
 *
 * Consider again the example seed-to-soil map:
 *
 * 50 98 2
 * 52 50 48
 * The first line has a destination range start of 50, a source range start of 98, and a range length of 2. This line means that the source range starts at 98 and contains two values: 98 and 99. The destination range is the same length, but it starts at 50, so its two values are 50 and 51. With this information, you know that seed number 98 corresponds to soil number 50 and that seed number 99 corresponds to soil number 51.
 *
 * The second line means that the source range starts at 50 and contains 48 values: 50, 51, ..., 96, 97. This corresponds to a destination range starting at 52 and also containing 48 values: 52, 53, ..., 98, 99. So, seed number 53 corresponds to soil number 55.
 *
 * Any source numbers that aren't mapped correspond to the same destination number. So, seed number 10 corresponds to soil number 10.
 *
 * So, the entire list of seed numbers and their corresponding soil numbers looks like this:
 *
 * seed  soil
 * 0     0
 * 1     1
 * ...   ...
 * 48    48
 * 49    49
 * 50    52
 * 51    53
 * ...   ...
 * 96    98
 * 97    99
 * 98    50
 * 99    51
 * With this map, you can look up the soil number required for each initial seed number:
 *
 * Seed number 79 corresponds to soil number 81.
 * Seed number 14 corresponds to soil number 14.
 * Seed number 55 corresponds to soil number 57.
 * Seed number 13 corresponds to soil number 13.
 * The gardener and his team want to get started as soon as possible, so they'd like to know the closest location that needs a seed. Using these maps, find the lowest location number that corresponds to any of the initial seeds. To do this, you'll need to convert each seed number through other categories until you can find its corresponding location number. In this example, the corresponding types are:
 *
 * Seed 79, soil 81, fertilizer 81, water 81, light 74, temperature 78, humidity 78, location 82.
 * Seed 14, soil 14, fertilizer 53, water 49, light 42, temperature 42, humidity 43, location 43.
 * Seed 55, soil 57, fertilizer 57, water 53, light 46, temperature 82, humidity 82, location 86.
 * Seed 13, soil 13, fertilizer 52, water 41, light 34, temperature 34, humidity 35, location 35.
 * So, the lowest location number in this example is 35.
 *
 * What is the lowest location number that corresponds to any of the initial seed numbers?
 */