import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Announcement {


    public Announcement() {
    }

    public String getAnnouncement() throws FileNotFoundException {
        File file = new File("Announcement.txt");
        Scanner scanner = new Scanner(file);
        return scanner.nextLine();
    }

    public void setAnnouncement(String announcement) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter("Announcement.txt");
        writer.print(announcement);
        writer.close();
    }
}
