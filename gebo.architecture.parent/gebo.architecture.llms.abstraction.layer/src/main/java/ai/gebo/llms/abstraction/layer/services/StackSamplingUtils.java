package ai.gebo.llms.abstraction.layer.services;

import java.lang.StackWalker;
import java.lang.StackWalker.StackFrame;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

public final class StackSamplingUtils {

    private StackSamplingUtils() {
    }

    private static final StackWalker STACK_WALKER =
            StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

    public static String sampleCallerPackages(int maxPackages) {
        return sampleCallerPackages(maxPackages, packageName -> false);
    }

    /**
     * Samples up to {@code maxPackages} distinct packages from the calling thread's
     * stack, innermost first, leaving out every frame whose package the given
     * predicate rejects. The whole stack is walked when needed, so the meaningful
     * callers are found even below deep framework or reactive plumbing.
     *
     * @param skipPackage returns true for a package name to leave out of the sample
     */
    public static String sampleCallerPackages(int maxPackages, Predicate<String> skipPackage) {
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

                // Leave this utility out of the sampled stack
                if (currentClass == StackSamplingUtils.class) {
                    continue;
                }

                String packageName = currentClass.getPackageName();

                if (packageName == null || packageName.isBlank()) {
                    packageName = "default";
                }

                if (skipPackage.test(packageName)) {
                    continue;
                }

                // Avoid consecutive repetitions of the same package
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
