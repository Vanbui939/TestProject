package utils.common;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class HtmlUtils {
  private static Document document;

  public HtmlUtils(String htmlText) {
    document = Jsoup.parse(htmlText);
  }

  public String getUrlFromLinkText(String hrefText) {
    // get all elements with <a> tag
    Elements links = document.select("a");
    for (int i = 0; i < links.size(); i++) {
      // find matching element
      if (links.get(i).text().contains(hrefText)) {
        // return href
        return links.get(i).attr("href");
      }
    }
    return null;
  }

  public String getRawText() {
    String content = "";
    // Span tag only for now
    // get all elements with <span> tag
    Elements spans = document.select("span");
    for (int i = 0; i < spans.size(); i++) {
      // only get span with text
      if (spans.get(i).getAllElements().size() == 1) {
        content += spans.get(i).text() + "\n";
      }
    }
    return content;
  }
}
