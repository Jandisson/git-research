package usp.br.jandisson.gitresearch.text.transformation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RemoveEspecialCharactersTransformation implements ITextTransformation {




    @Override
    public String transform(String text) {

        return text.replaceAll("[^a-zA-Z0-9]", " ");
    }


}
