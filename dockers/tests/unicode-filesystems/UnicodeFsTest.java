import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;
public class UnicodeFsTest {
  public static void main(String[] a) throws Exception {
    System.out.println("java " + System.getProperty("java.version") + " sun.jnu.encoding=" + System.getProperty("sun.jnu.encoding") + " user=" + System.getProperty("user.name"));
    int ok = 0, fail = 0;
    List<String> expected;
    try { expected = Files.readAllLines(Paths.get(a[1]), StandardCharsets.UTF_8).stream().filter(s -> !s.isEmpty()).toList(); }
    catch (Exception e) { System.out.println("[FAIL] cannot read names list: " + e); System.exit(2); return; }
    Path root;
    try { root = Paths.get(a[0]); } catch (Exception e) { System.out.println("[FAIL] " + e); System.exit(2); return; }
    // 1) discovery: walk the mounted tree the way a content handler does
    List<String> found = new ArrayList<>();
    try (Stream<Path> w = Files.walk(root)) {
      w.filter(Files::isRegularFile).forEach(p -> found.add(root.relativize(p).toString()));
    } catch (Exception e) { System.out.println("[FAIL] walk: " + e); }
    long mangled = found.stream().filter(s -> s.indexOf('�') >= 0 || s.contains("?")).count();
    System.out.println("walk: found " + found.size() + " files, " + mangled + " with mangled names");
    // 2) every discovered file must open and contain its own name
    for (String rel : found) {
      try {
        String content = Files.readString(root.resolve(rel), StandardCharsets.UTF_8);
        boolean match = content.equals(rel) || java.text.Normalizer.normalize(content, java.text.Normalizer.Form.NFC).equals(java.text.Normalizer.normalize(rel, java.text.Normalizer.Form.NFC));
        if (match) ok++; else { fail++; System.out.println("[FAIL] content mismatch: " + rel); }
      } catch (Exception e) { fail++; System.out.println("[FAIL] open " + rel + " -> " + e.getClass().getSimpleName() + ": " + e.getMessage()); }
    }
    // 3) direct lookup by the expected (Java-side) name - the path a stored document reference takes
    int direct = 0;
    for (String n : expected) {
      try { if (Files.isRegularFile(root.resolve(n))) direct++; else System.out.println("[FAIL] not found by name: " + n); }
      catch (Exception e) { System.out.println("[FAIL] lookup " + n + " -> " + e.getClass().getSimpleName()); }
    }
    System.out.println("RESULT expected=" + expected.size() + " walked=" + found.size() + " readOK=" + ok + " readFAIL=" + fail + " directLookupOK=" + direct);
    System.exit(ok == expected.size() && direct == expected.size() && fail == 0 ? 0 : 1);
  }
}
