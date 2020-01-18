package Card.exporter;

import Card.Card;
import Card.CardConfig;
import Card.converter.MapToListConverter;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.*;

public class PDFCardsExporter implements CardExporter {

    private static String CARD_TITLE = "CARD_TITLE";
    private static Locale MY_LOCALE = Locale.ITALIAN;
    private static int BORDER_WIDTH = 2;
    private static float SPACING = 15.0f;
    private static float CELL_HEIGHT = 25.0f;

    private CardTranslations translations;
    private String fileName;
    private String fileExtension;
    private List<Card> cards;
    private int currentYear;

    public PDFCardsExporter(String fileName, List<Card> cards) {
        initTranslations();
        this.fileName = fileName;
        this.fileExtension = "pdf";
        this.cards = cards;
        this.currentYear = Calendar.getInstance().get(Calendar.YEAR);
    }

    private void initTranslations() {
        this.translations = new CardTranslations();
        Map<String, String> translationPerKey = new HashMap<String, String>() {{
            put("CARD_TITLE", "Tombola A.I.G Santo Natale %d");
        }};
        translations.addTranslation(Locale.ITALIAN, translationPerKey);
    }

    private String getFileName() {
        return fileName + "." + fileExtension;
    }

    @Override
    public void export() {
        try {
            createPdf();
        } catch (DocumentException | IOException | URISyntaxException e) {
            System.out.println(String.format("Something went wrong with the PDF export: %s", e.getMessage()));
        }
    }

    private void createPdf() throws DocumentException, IOException, URISyntaxException {
        Document pdfDoc = new Document();
        PdfWriter.getInstance(pdfDoc, new FileOutputStream(getFileName(), true));
        pdfDoc.open();

        for (Card card : cards) {
            CardConfig cardConfig = card.getCardConfig();

            PdfPTable table = createAndInitCard(cardConfig);
            addTableHeaderToCard(table, cardConfig);
            addLogoToCard(table, cardConfig);
            addNumbersToCard(table, cardConfig, card.getGeneratedNumbers());

            pdfDoc.add(table);
        }

        pdfDoc.close();
    }

    private void addNumbersToCard(PdfPTable table, CardConfig cardConfig, Map<Integer, List<Integer>> generatedNumbers) {
        MapToListConverter converter = new MapToListConverter<>(cardConfig.getRowNumber(), cardConfig.getColumnNumber());
        Map<Integer, List<String>> numbersPerRows = converter.convert(generatedNumbers);
        if (numbersPerRows.keySet().size() < cardConfig.getRowNumber()) {

        }
        numbersPerRows.forEach((row, numbers) -> addNumbersToTable(table, numbers));
    }

    private PdfPTable createAndInitCard(CardConfig cardConfig) {
        PdfPTable table = new PdfPTable(cardConfig.getColumnNumber() + 2);
        table.setSpacingBefore(SPACING);
        table.setSpacingAfter(SPACING);
        return table;
    }

    private void addTableHeaderToCard(PdfPTable table, CardConfig cardConfig) {
        Phrase headerText = new Phrase(String.format(translations.getTranslation(MY_LOCALE, CARD_TITLE), currentYear));
        headerText.setFont(FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK));
        PdfPCell header = new PdfPCell(headerText);
        header.setHorizontalAlignment(Element.ALIGN_CENTER);
        header.setBackgroundColor(BaseColor.GREEN);
        header.setColspan(cardConfig.getColumnNumber());
        header.setBorderWidth(BORDER_WIDTH);
        header.setRole(PdfName.HEADERS);
        table.addCell(header);
    }

    private void addNumbersToTable(PdfPTable table, List<String> numbers) {
        for (String value : numbers) {
            PdfPCell cell = new PdfPCell(new Phrase(value));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_CENTER);
            cell.setFixedHeight(CELL_HEIGHT);
            table.addCell(cell);
        }
    }

    private void addLogoToCard(PdfPTable table, CardConfig cardConfig) throws URISyntaxException, BadElementException, IOException {
        Path path = Paths.get(ClassLoader.getSystemResource("logo_large.jpg").toURI());
        Image img = Image.getInstance(path.toAbsolutePath().toString());
        PdfPCell imageCell = new PdfPCell(img, true);
        imageCell.setRowspan(cardConfig.getRowNumber() + 1);
        imageCell.setColspan(2);
        table.addCell(imageCell);
    }
}
