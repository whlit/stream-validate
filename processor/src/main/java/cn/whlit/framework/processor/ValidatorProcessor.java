package cn.whlit.framework.processor;

import cn.whlit.framework.processor.type.TypeMessage;
import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypesException;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.util.List;
import java.util.Set;

/**
 * @author WangHaiLong 2024/5/3 17:39
 */
@AutoService(Processor.class)
@SupportedAnnotationTypes("cn.whlit.framework.processor.Validator")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ValidatorProcessor extends AbstractProcessor {

    private Filer filer;
    private Elements elementUtils;
    private Types typeUtils;
    private Messager messager;
    private TypeHandle typeHandle;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        elementUtils = processingEnv.getElementUtils();
        typeUtils = processingEnv.getTypeUtils();
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
        typeHandle = new TypeHandle(elementUtils, typeUtils);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotation : annotations) {
            Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(annotation);

            for (Element element : elements) {
                Validator validator = element.getAnnotation(Validator.class);
                if (validator == null) {
                    continue;
                }
                try {
                    Class<?>[] ignore = validator.value();
                } catch (MirroredTypesException e) {
                    List<? extends TypeMirror> typeMirrors = e.getTypeMirrors();
                    for (TypeMirror typeMirror : typeMirrors) {
                        TypeMessage typeMessage = typeHandle.getTypeMessage(typeMirror);
                    }
                } catch (Exception e) {
                    messager.printMessage(Diagnostic.Kind.WARNING, e.getMessage(), element);
                }

            }
        }
        return false;
    }
}
