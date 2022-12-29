package xyzbank.utils;

import java.util.Map;

public class BrowserFabric {
    private static final Map<BrowserTypeEnum, IBrowserProcessor> PROCESSORS = Map.of(
            BrowserTypeEnum.CHROME, new InitChromeBrowser()
    );

    private static final IBrowserProcessor DEFAULT_PROCESSOR = new BrowserTypeException("Incorrect Browser");

    public static IBrowserProcessor getProcessor(BrowserTypeEnum type) {
        return PROCESSORS.getOrDefault(type, DEFAULT_PROCESSOR);
    }
}
