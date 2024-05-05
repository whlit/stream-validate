package cn.whlit.framework.processor;

import cn.whlit.framework.generate.ClassGenerator;
import cn.whlit.framework.parse.TypeParser;
import cn.whlit.framework.processor.type.TypeMessage;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypesException;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.io.IOException;
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
    private ProcessContext context;


    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
        context = new ProcessContext(processingEnv.getElementUtils(), processingEnv.getTypeUtils(), processingEnv.getMessager());

    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
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
                context.setPackageName(context.elementUtils.getPackageOf(element).getQualifiedName().toString());
                context.setClassNameSuffix(element.getSimpleName().toString());
                try {
                    Class<?>[] ignore = validator.value();
                } catch (MirroredTypesException e) {
                    List<? extends TypeMirror> typeMirrors = e.getTypeMirrors();
                    processTypeMirrors(typeMirrors, context);
                } catch (Exception e) {
                    context.messager.printMessage(Diagnostic.Kind.WARNING, e.getMessage(), element);
                }
            }
        }
        return false;
    }

    private void processTypeMirrors(List<? extends TypeMirror> typeMirrors, ProcessContext context) {
        for (TypeMirror typeMirror : typeMirrors) {
            TypeMessage typeMessage = TypeParser.parse(context, typeMirror);
            if (typeMessage != null) {
                TypeSpec typeSpec = ClassGenerator.generate(context, typeMessage);
                JavaFile javaFile = JavaFile.builder(context.getPackageName(), typeSpec).build();
                try {
                    javaFile.writeTo(filer);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }


}
