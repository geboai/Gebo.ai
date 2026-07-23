package ai.gebo.llms.abstraction.layer.services;

import java.lang.StackWalker;
import java.lang.StackWalker.StackFrame;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class StackSamplingUtils {

    private StackSamplingUtils() {
    }

    private static final StackWalker STACK_WALKER =
            StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

    public static String sampleCallerPackages(int maxPackages) {
        if (maxPackages <= 0) {
            return "";
        }

        return STACK_WALKER.walk(frames -> {
            Iterator<StackFrame> iterator = frames.iterator();

            List<String> packages = new ArrayList<>(maxPackages);
            String previousPackage = null;

            while (iterator.hasNext() && packages.size() < maxPackages) {
                StackFrame frame = iterator.next();
                Class<?> currentClass = frame.getDeclaringClass();

                // Esclude questa utility dallo stack campionato
                if (currentClass == StackSamplingUtils.class) {
                    continue;
                }

                String packageName = currentClass.getPackageName();

                if (packageName == null || packageName.isBlank()) {
                    packageName = "default";
                }

                // Evita ripetizioni consecutive dello stesso package
                if (packageName.equals(previousPackage)) {
                    continue;
                }

                packages.add(packageName);
                previousPackage = packageName;
            }

            return String.join(",", packages);
        });
    }
}