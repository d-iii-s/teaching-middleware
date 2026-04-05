import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;

import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.SlidingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

public class Counter {
    public static void main (String [] arguments) {
        try {
            StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment ();

            environment
                .socketTextStream (Shared.HOST, Shared.SOURCE_PORT, "\n")
                .flatMap ((String line, Collector <Character> collector) -> {
                    for (char character : line.toLowerCase ().toCharArray ()) {
                        if (Character.isLetter (character)) {
                            collector.collect (character);
                        }
                    }
                })
                .returns (Character.class)
                .keyBy ((letter) -> letter)
                .window (SlidingProcessingTimeWindows.of (Time.seconds (11), Time.seconds (1)))
                .aggregate (new LetterCountAggregator (), new LetterCountFormatter())
                // The socket sink is intended for debugging and does not participate in checkpointing.
                .writeToSocket (Shared.HOST, Shared.MONITOR_PORT, new SimpleStringSchema ());

            environment.execute ("Counter");
        }
        catch (Exception e) {
            System.out.println (e);
            e.printStackTrace ();
        }
    }

    static class LetterCountAggregator implements AggregateFunction <Character, Long, Long> {
        @Override public Long createAccumulator () { return 0L; }
        @Override public Long add (Character value, Long accumulator) { return accumulator + 1; }
        @Override public Long getResult (Long accumulator) { return accumulator; }
        @Override public Long merge (Long left, Long right) { return left + right; }
    }

    static class LetterCountFormatter extends ProcessWindowFunction <Long, String, Character, TimeWindow> {
        private String formatTime (long timestamp) {
            LocalTime time = Instant.ofEpochMilli (timestamp).atZone (ZoneId.systemDefault ()).toLocalTime ();
            return String.format ("%02d:%02d:%02d", time.getHour (), time.getMinute (), time.getSecond ());
        }

        @Override
        public void process (Character key, Context context, Iterable <Long> elements, Collector <String> collector) {
            long count = elements.iterator ().next ();
            String windowStart = formatTime (context.window ().getStart ());
            String windowEnd = formatTime (context.window ().getEnd ());
            collector.collect (String.format ("%s-%s %s %d\n", windowStart, windowEnd, key, count));
        }
    }
}
