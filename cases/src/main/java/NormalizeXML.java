import java.io.*;
import java.util.*;


// A class with various methods for handling the xml in this project
public class NormalizeXML implements Runnable{

    private File file;

    public NormalizeXML(File file) {
        this.file = file;
    }

    public void run() {
        // Read file into scanner
        List<String> lines = new ArrayList<>();
        try (Scanner in = new Scanner(file)) {
            while (in.hasNextLine())
                lines.add(in.nextLine());
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        // Normalize
        if(!lines.get(0).contains("<root>")){
            try (PrintStream out = new PrintStream(file)) {
                char current;
                out.println("<root>");
                for (String line : lines) {
                    out.print("    ");
                    for (int i = 0; i < line.length(); i++) {
                        current = line.charAt(i);
                        if ((current == 0x9) ||
                                (current == 0xA) ||
                                (current == 0xD) ||
                                ((current >= 0x20) && (current <= 0xD7FF)) ||
                                ((current >= 0xE000) && (current <= 0xFFFD)) ||
                                ((current >= 0x10000) && (current <= 0x10FFFF))){
                            out.print(Character.toString(current));
                        }
                    }
                    out.println();
                }
                out.println("</root>");
                out.close();
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
            System.out.println(file.getName() + " normalized.");
        }
        else{
            System.out.println(file.getName() + " already normalized.");
        }

    }
}
