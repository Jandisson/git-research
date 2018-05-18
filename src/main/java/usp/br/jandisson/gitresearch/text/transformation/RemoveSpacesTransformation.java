package usp.br.jandisson.gitresearch.text.transformation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RemoveSpacesTransformation implements ITextTransformation {




    @Override
    public String transform(String text) {


        text =text.replaceAll("\\s+", " ");
        return text;
    }


}
