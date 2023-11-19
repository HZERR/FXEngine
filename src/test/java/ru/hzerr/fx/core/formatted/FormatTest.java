package ru.hzerr.fx.core.formatted;

import org.junit.jupiter.api.Test;
import ru.hzerr.fx.engine.configuration.fs.typesafe.FormatException;

public class FormatTest {

    private final String MESSAGE = "{} w{o\\}rld{{}}";
    private final String[] ARGS = new String[] {"Hello", "!"};

    @Test
    void getStringTest() {
        StringBuilder sb = new StringBuilder();
        int msgIndex = 0;
        int argsIndex = 0;
        while (msgIndex < MESSAGE.length() - 1) {
            if (MESSAGE.charAt(msgIndex) == '{' && MESSAGE.charAt(msgIndex + 1) == '}') {
                if (argsIndex < ARGS.length) {
                    sb.append(ARGS[argsIndex]);
                    msgIndex = msgIndex + 2;
                    argsIndex = argsIndex + 1;
                } else
                    throw new FormatException("Invalid arguments length. The FXEngine has identified " + (ARGS.length + 1) + " places to insert. Check your message: " + MESSAGE);
            } else
                sb.append(MESSAGE.charAt(msgIndex++));
        }

        if (msgIndex != MESSAGE.length()) {
            sb.append(MESSAGE.charAt(MESSAGE.length() - 1));
        }

        System.out.println(sb);
    }

}
