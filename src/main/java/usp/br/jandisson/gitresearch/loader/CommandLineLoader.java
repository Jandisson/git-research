package usp.br.jandisson.gitresearch.loader;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import usp.br.jandisson.gitresearch.text.normalization.DescriptionNormalizer;
import usp.br.jandisson.gitresearch.text.normalization.IDescriptionNormalizer;

import java.io.File;

@Component
public class CommandLineLoader implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        IDescriptionNormalizer normalizer = new DescriptionNormalizer();
        System.out.println(normalizer.normalize(new File("D:\\projetos\\8319\\readme.md")));

    }
}
