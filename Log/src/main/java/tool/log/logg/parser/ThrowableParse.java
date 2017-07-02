package tool.log.logg.parser;

import android.util.Log;

/**
 * Throwable
 */
public class ThrowableParse implements Parser<Throwable> {

    @Override
    public Class<Throwable> parseClassType() {
        return Throwable.class;
    }

    @Override
    public String parseString(Throwable throwable) {
        return Log.getStackTraceString(throwable);
    }
}
