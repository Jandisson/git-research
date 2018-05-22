package usp.br.jandisson.gitresearch.text.transformation;

import java.util.ArrayList;
import java.util.List;

public class DefaultTransformationGroup implements ITransformationGroup {

    private List<ITextTransformation> transformations;

    public DefaultTransformationGroup(List<ITextTransformation> transformations) {
        this.transformations = transformations;
    }

    public DefaultTransformationGroup() {
        this.transformations = new ArrayList<ITextTransformation>();
        this.transformations.add(new RemoveUrlTransformation());
        this.transformations.add(new RemoveXmlTransformation());
        this.transformations.add(new RemoveEspecialCharactersTransformation());
        this.transformations.add(new RemoveSpacesTransformation());
        this.transformations.add(new RemoveNumbersTransformation());
        this.transformations.add(new LowerCaseTransformation());
        this.transformations.add(new RemoveStopWordsTransformation());
    }



    @Override
    public String transform(String text) {
        for(ITextTransformation transformation: this.transformations)
        {
            text = transformation.transform(text);
        }
        return text;
    }




}
