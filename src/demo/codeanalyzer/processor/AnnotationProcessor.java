package demo.codeanalyzer.processor;

import com.sun.source.util.TreePath;
import com.sun.source.util.Trees;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

/**
 * Created by xiachen on 3/15/15.
 */
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("*")
public class AnnotationProcessor extends AbstractProcessor {

    private Trees trees;

    @Override
    public void init(ProcessingEnvironment pe) {
        super.init(pe);
        trees = Trees.instance(pe);
    }

    /**
     * Processes the annotation types defined for this processor.
     *
     * @param annotations      the annotation types requested to be processed
     * @param roundEnvironment environment to get information about the current and prior
     *                         round
     * @return whether or not the set of annotations are claimed by this
     * processor
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnvironment) {
        CodeAnalyzerTreeVisitor visitor = new CodeAnalyzerTreeVisitor();
        if (!roundEnvironment.processingOver()) {
            for (Element element : roundEnvironment.getRootElements()) {
                TreePath tp = trees.getPath(element);
                visitor.scan(tp, trees);
            }
        } else {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "assertion visit finished");
        }
        return true;
    }
}
