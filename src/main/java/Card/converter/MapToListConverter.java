package Card.converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapToListConverter implements Converter<Map<Integer, List<Integer>>, Map<Integer, List<String>>> {

    private int rows;
    private int columns;

    public MapToListConverter(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
    }

    @Override
    public Map<Integer, List<String>> convert(Map<Integer, List<Integer>> data) {
        return toMapOfList(data);
    }

    private Map<Integer, List<String>> toMapOfList(Map<Integer, List<Integer>> data) {
        Map<Integer, List<String>> transposedMap = createDefaultMap();
        for (Integer rowIndex : transposedMap.keySet()) {
            for (Integer columnIndex : data.keySet()) {
                String numberAsString = getCardNumberAsString(data, rowIndex, columnIndex);
                transposedMap.get(rowIndex).add(numberAsString);
            }
        }

        return transposedMap;
    }

    private String getCardNumberAsString(Map<Integer, List<Integer>> data, Integer rowIndex, Integer columnIndex) {
        return data.get(columnIndex).size() > rowIndex ? data.get(columnIndex).get(rowIndex).toString() : "";
    }

    private Map<Integer, List<String>> createDefaultMap() {
        HashMap<Integer, List<String>> defaultMap = new HashMap<>();
        for (int keyIndex = 0; keyIndex < rows; keyIndex++) {
            defaultMap.put(keyIndex, new ArrayList<>(columns));
        }
        return defaultMap;
    }
}
