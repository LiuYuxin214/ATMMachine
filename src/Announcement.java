import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Announcement {

    private String announcement;

    public Announcement() {
        announcement = "Default announcement";
    }

    public String getAnnouncement() throws FileNotFoundException {
        File file = new File("Announcement.txt");
        Scanner scanner = new Scanner(file);
        announcement = scanner.nextLine();
        return announcement;
    }

    public void setAnnouncement(String announcement) throws FileNotFoundException {
        this.announcement = announcement;
        File file = new File("Announcement.txt");
        PrintWriter writer = new PrintWriter(file);
        writer.print(announcement);
        writer.close();
    }
}
