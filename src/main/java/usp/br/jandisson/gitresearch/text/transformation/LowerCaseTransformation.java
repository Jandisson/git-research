package usp.br.jandisson.gitresearch.text.transformation;

public class LowerCaseTransformation implements ITextTransformation {




    @Override
    public String transform(String text) {


        text =text.toLowerCase();
        return text;
    }


}
