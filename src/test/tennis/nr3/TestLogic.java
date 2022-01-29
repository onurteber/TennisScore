package test.tennis.nr3;

import com.tennis.nr3.Main;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

public class TestLogic {

    private File output;

    @Before
    public void setUp() throws Exception {
        output = new File("files/test-output.txt");
    }

    @Test
    public void testEmptyLine() throws IOException {
        Files.write(Paths.get("files/test-input.txt"), "".getBytes(StandardCharsets.UTF_8));
        Main.calculate("test-input.txt", "test-output.txt");

        Scanner myReader = new Scanner(output);

        assertEquals(myReader.hasNextLine(), false);
    }

    @Test
    public void testOneLine() throws IOException {
        Files.write(Paths.get("files/test-input.txt"), "AB".getBytes(StandardCharsets.UTF_8));
        Main.calculate("test-input.txt", "test-output.txt");

        Scanner myReader = new Scanner(output);
        assertEquals(myReader.nextLine(), "0-0 15-15");
    }

    @Test
    public void testDrawMatch() throws IOException {
        Files.write(Paths.get("files/test-input.txt"), "AAABBBAA".getBytes(StandardCharsets.UTF_8));
        Main.calculate("test-input.txt", "test-output.txt");

        Scanner myReader = new Scanner(output);
        assertEquals(myReader.nextLine(), "1-0");
    }

    @Test
    public void testDifferentCharacter() throws IOException {
        Files.write(Paths.get("files/test-input.txt"), "QWERtyuugdfdj\nasdadsas".getBytes(StandardCharsets.UTF_8));
        Main.calculate("test-input.txt", "test-output.txt");

        Scanner myReader = new Scanner(output);
        assertEquals(myReader.hasNextLine(), false);
    }

    @Test
    public void testLongGame() throws IOException {
        Files.write(Paths.get("files/test-input.txt"), "AAAABBBBAAAABBBBAAAABBBBAAAAAAAAAAAABBBBAAAABBBBAAAABBBBAAAABBBBAAAABBBBBBBBA".getBytes(StandardCharsets.UTF_8));
        Main.calculate("test-input.txt", "test-output.txt");

        Scanner myReader = new Scanner(output);
        assertEquals(myReader.nextLine(), "6-3 4-6 0-0 15-0");
    }

    @Test
    public void testAdvantageGame() throws IOException {
        Files.write(Paths.get("files/test-input.txt"), "BBBAAAA".getBytes(StandardCharsets.UTF_8));
        Main.calculate("test-input.txt", "test-output.txt");

        Scanner myReader = new Scanner(output);
        assertEquals(myReader.nextLine(), "0-0 A-40");
    }

    @Test
    public void testReturnFromAdvantageGame() throws IOException {
        Files.write(Paths.get("files/test-input.txt"), "BBBAAAAB".getBytes(StandardCharsets.UTF_8));
        Main.calculate("test-input.txt", "test-output.txt");

        Scanner myReader = new Scanner(output);
        assertEquals(myReader.nextLine(), "0-0 40-40");
    }
}
