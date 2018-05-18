package usp.br.jandisson.gitresearch.text.transformation;

import org.nibor.autolink.LinkExtractor;
import org.nibor.autolink.LinkSpan;
import org.nibor.autolink.LinkType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EnumSet;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RemoveUrlTransformation implements ITextTransformation {


    Logger logger = LoggerFactory.getLogger(RemoveUrlTransformation.class);

    @Override
    public String transform(String text) {

        String originalText = text;

        LinkExtractor linkExtractor = LinkExtractor.builder()
                .linkTypes(EnumSet.of(LinkType.URL, LinkType.WWW, LinkType.EMAIL))
                .build();

        Iterable<LinkSpan> links = linkExtractor.extractLinks(originalText);


        Iterator<LinkSpan> i=links.iterator();
        while(i.hasNext())
        {
            LinkSpan linkSpan = i.next();
            String link = originalText.substring(linkSpan.getBeginIndex(), linkSpan.getEndIndex());
            text= text.replace(link,"" );
        }

        return text;
    }


}
