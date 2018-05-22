package usp.br.jandisson.gitresearch.text.normalization;





import org.apache.commons.io.FileUtils;
import usp.br.jandisson.gitresearch.text.transformation.*;

import java.io.File;
import java.io.IOException;

public class DescriptionNormalizer implements IDescriptionNormalizer{

    ITransformationGroup transformations;

    public DescriptionNormalizer() {
       this.configureTransformations();
    }

    @Override
    public String normalize(File file) throws IOException {

       String text = FileUtils.readFileToString(file,"UTF8");

       return transformations.transform(text);

    }

    private void configureTransformations(){
        this.transformations = new DefaultTransformationGroup();
    }
}
