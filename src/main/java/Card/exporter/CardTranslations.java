package Card.exporter;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CardTranslations {

    private Map<Locale, Map<String, String>> textPerLocale;

    public CardTranslations() {
        this.textPerLocale = new HashMap<>();
    }

    public void addTranslation(Locale language, Map<String, String> translations) {
        this.textPerLocale.put(language, translations);
    }

    public String getTranslation(Locale language, String key) {
        Map<String, String> stringStringMap = this.textPerLocale.get(language);
        return stringStringMap.get(key);
    }

}
