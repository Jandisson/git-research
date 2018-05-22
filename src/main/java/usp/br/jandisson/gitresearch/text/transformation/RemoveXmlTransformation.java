package usp.br.jandisson.gitresearch.text.transformation;

import org.jsoup.Jsoup;

public class RemoveXmlTransformation implements ITextTransformation{

    @Override
    public String transform(String text) {
        return Jsoup.parse(text).text();
    }
}
