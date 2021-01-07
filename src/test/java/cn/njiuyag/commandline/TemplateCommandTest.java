package cn.njiuyag.commandline;

import org.junit.Test;

public class TemplateCommandTest {

    @Test
    public void runTest() {
        TemplateCommand templateCommand = new TemplateCommand();
        templateCommand.setCommand("screw");
        templateCommand.run();
    }
}