package dgroomes.caching;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class Util {

  /**
   * Read a classpath resource into a string.
   */
  public static String readClasspathResource(String path) {
    try (InputStream stream = Util.class.getResourceAsStream(path);) {

      Objects.requireNonNull(stream, () -> "Classpath resource '%s' not found. Did you forget the leading forward slash ('/') ?.".formatted(path));
      byte[] bytes = stream.readAllBytes();
      return new String(bytes);
    } catch (IOException e) {
      var msg = "Unexpected exception while reading classpath resource '%s'";
      throw new IllegalStateException(msg.formatted(path));
    }
  }
}
