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
            }

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
