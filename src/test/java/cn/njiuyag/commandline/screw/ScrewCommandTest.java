package cn.njiuyag.commandline.screw;

import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class ScrewCommandTest {

    @Test
    public void run() {
        ScrewCommand screwCommand = new ScrewCommand();
        URL path = this.getClass().getClassLoader().getResource("screw.properties");
        File file = new File(path.getPath());
        screwCommand.setConfigFile(file);
        screwCommand.run();
    }
}