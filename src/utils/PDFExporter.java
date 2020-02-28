package utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class PDFExporter implements Runnable
{
    private static final String FILEPATH = "/Downloads/chat.pdf";

    private String chatText;

    public PDFExporter(String chatText) {
        this.chatText = chatText;
    }

    @Override
    public void run()
    {
        try
        {
            PDDocument doc = new PDDocument();
            PDPage myPage = new PDPage();
            doc.addPage(myPage);

            PDPageContentStream content = new PDPageContentStream(doc, myPage);

            content.beginText();

            content.setFont(PDType1Font.TIMES_ROMAN, 12);
            content.setLeading(14.5f);
            content.newLineAtOffset(25, 700);

            String[] splitText = chatText.split("\n");

            for (int i = 0; i < splitText.length; i++)
            {
                content.showText( splitText[i] );
                content.newLine();
            }

            content.endText();
            content.close();

            doc.save(System.getProperty("user.home") + FILEPATH);
            doc.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
