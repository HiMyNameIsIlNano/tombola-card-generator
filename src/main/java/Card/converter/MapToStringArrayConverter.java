package Card.converter;

import java.util.List;
import java.util.Map;

public class MapToStringArrayConverter implements Converter<Map<Integer, List<Integer>>, String[]> {

    private int rows;
    private int columns;

    public MapToStringArrayConverter(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
    }

    @Override
    public String[] convert(Map<Integer, List<Integer>> data) {
        String[] result = new String[columns * rows];

        for (Map.Entry<Integer, List<Integer>> integerListEntry : data.entrySet()) {
            int bucketIndex = integerListEntry.getKey();
            for (int k = 0; k < rows; k++) {
                int toArrayIndex = columns * k + bucketIndex;
                try {
                    result[toArrayIndex] = Integer.toString(integerListEntry.getValue().get(k));
                } catch (IndexOutOfBoundsException e) {
                    result[toArrayIndex] = "";
                }
            }
        }

        return result;
    }
}
