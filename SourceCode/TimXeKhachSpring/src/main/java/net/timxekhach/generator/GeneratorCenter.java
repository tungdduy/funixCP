package net.timxekhach.generator;

import net.timxekhach.generator.message.MessageGenerator;
import net.timxekhach.generator.url.UrlGenerator;

public class GeneratorCenter {

    public static void main(String[] args) {
        UrlGenerator.run();
        MessageGenerator.run();
    }
}
