package Card.generator;

import Card.CardConfig;

import java.util.List;
import java.util.Map;

public interface CardGenerator {

    Map<Integer, List<Integer>> generate(CardConfig seed);

}
