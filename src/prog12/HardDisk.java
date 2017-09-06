package prog12;

import java.util.*;

public class HardDisk<T> extends TreeMap<Long, T> {
  public Long newFile () {
    nextIndex += 1 + random.nextInt(4);
    return nextIndex;
  }

  private Long nextIndex = 0L;
  private Random random = new Random(0);
}
