import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Iterator;



public class Main {

    public static void main(String[] args) {
        if (args.length > 0) {
            // If a command file is provided as argument, run it
            CmdParser.parse("run " + args[0]);
        } else {
            // Otherwise, start reading from standard input
            CmdParser.start(System.in);
        }
    }
}