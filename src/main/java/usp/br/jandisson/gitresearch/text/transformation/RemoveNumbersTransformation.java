package usp.br.jandisson.gitresearch.text.transformation;

public class RemoveNumbersTransformation implements ITextTransformation {




    @Override
    public String transform(String text) {


        text =text.replaceAll("\\s+[0-9]+", " ");
        return text;
    }


}
