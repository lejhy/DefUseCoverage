import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javassist.*;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;

public class DefUseCallInjector implements ClassFileTransformer{

    public static final boolean debugMode = false;

    // Workaround to exclude javaagent classes from being injected
    public static final List<String> classesToExclude = new ArrayList<String>(Arrays.asList(
            "Agent",
            "DataAccessLogEntry",
            "DefUseDataCollector")
    );

    private ClassLoader systemClassLoader;
    private ClassPool classPool;
    private ExprEditor editor;

    public DefUseCallInjector() {
        this.systemClassLoader = ClassLoader.getSystemClassLoader();
        this.classPool = ClassPool.getDefault();
        this.editor = new DefUseCallEditor();
    }

    @Override
    public byte[] transform(ClassLoader loader,
                            String className,
                            Class classBeingRedefined,
                            ProtectionDomain protectionDomain,
                            byte[] classfileBuffer)
            throws IllegalClassFormatException {
        // Only modify application classes and exclude monitoring classes
        if (loader == systemClassLoader && !classesToExclude.contains(className)) {
            try {
                // Get the Javassist representation of the class
                CtClass ctClass = classPool.get(className);
                if (debugMode) System.out.println("Transforming class: " + ctClass.getName());
                // Get all methods of this class and loop through them
                CtMethod[] ctMethods = ctClass.getDeclaredMethods();
                for (CtMethod ctMethod : ctMethods) {
                    if (debugMode) System.out.println("Transforming method: " + ctMethod.getName());
                    // Instrument method with ExprEditor
                    ctMethod.instrument(editor);
                    // Look for the main method signature, we want to add extra call to it
                    if (ctMethod.getModifiers() == Modifier.PUBLIC + Modifier.STATIC && ctMethod.getName().equals("main")) {
                        System.out.println("IN");
                        // Add a call to end of the method to output results
                        ctMethod.insertAfter("{ DefUseDataCollector.get().getResults(); }", true);
                    }
                }
                return ctClass.toBytecode();
            } catch (NotFoundException | IOException | CannotCompileException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private class DefUseCallEditor extends ExprEditor {
        @Override
        public void edit(FieldAccess f) throws CannotCompileException{
            String accessType = "";
            if (f.isWriter()) accessType = "write";
            else if (f.isReader()) accessType = "read";
            if (debugMode) System.out.println("Editing field " + accessType + ": " + f.getFieldName());

            // Get all the required info about the access
            char readOrWrite = accessType.charAt(0);
            int lineNumber = f.getLineNumber();
            String enclosingClassName = f.getEnclosingClass().getName();
            String fieldName = f.getFieldName();
            String className = f.getClassName();

            // Register the fieldAccesss with the monitoring class
            DefUseDataCollector.get().register(readOrWrite, lineNumber, enclosingClassName, fieldName, className);

            // Inject the reporting code into the method
            f.replace("{" +
                    "DefUseDataCollector.get().getLock().lock();" +
                    "$_ = $proceed($$);" +
                    "DefUseDataCollector.get().report(\'"+readOrWrite+"\',"+lineNumber+",\""+enclosingClassName+"\",\""+fieldName+"\",\""+className+"\",Thread.currentThread().getId());" +
                    "DefUseDataCollector.get().getLock().unlock();" +
                    "}"
            );
        }
    }
}
