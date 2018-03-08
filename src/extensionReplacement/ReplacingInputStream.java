package extensionReplacement;

import java.io.*;
import java.util.*;

class ReplacingInputStream extends FilterInputStream {

    private static int howManyChanges = 0;

    private LinkedList<Integer> inQueue = new LinkedList<>();
    private LinkedList<Integer> outQueue = new LinkedList<>();
    private final byte[] search, replacement;

    ReplacingInputStream(InputStream in,
                         byte[] search,
                         byte[] replacement) {
        super(in);
        this.search = search;
        this.replacement = replacement;
    }

    private boolean isMatchFound() {
        Iterator<Integer> inIter = inQueue.iterator();
        for (byte aSearch : search)
            if (!inIter.hasNext() || aSearch != inIter.next())
                return false;
        return true;
    }

    private void readAhead() throws IOException {
        while (inQueue.size() < search.length) {
            int next = super.read();
            inQueue.offer(next);
            if (next == -1)
                break;
        }
    }

    @Override
    public int read() throws IOException {
        if (outQueue.isEmpty()) {
            readAhead();

            if (isMatchFound()) {
                for (byte ignored : search) inQueue.remove();

                for (byte b : replacement)
                    outQueue.offer((int) b);
                howManyChanges++;
            } else
                outQueue.add(inQueue.remove());
        }

        return outQueue.remove();
    }

    static int getHowMany() {
        return howManyChanges;
    }

    static void setHowMany(int x){
        howManyChanges = x;
    }
}