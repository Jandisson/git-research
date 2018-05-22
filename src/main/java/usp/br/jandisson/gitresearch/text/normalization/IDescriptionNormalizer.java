package usp.br.jandisson.gitresearch.text.normalization;

import java.io.File;
import java.io.IOException;

public interface IDescriptionNormalizer {

    String normalize(File file) throws IOException;
}
