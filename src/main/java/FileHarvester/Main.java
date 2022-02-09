package FileHarvester;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {

        Scanner in = new Scanner(System.in);
        System.out.println("Bitte wählen zwischen lokalem Dateipfad (l) oder Netzwerk-Ordner (n).");
        String fileSystem = in.nextLine();
        System.out.println("Bitte den Wurzel-Datei-Pfad der zu durchsuchenden Ordnerstruktur angeben.");
        System.out.println("Beim Netzwerk-Ordner ist //fsfl.fernuni-hagen.de/fl-groups$/ bereits vorgegeben.");
        System.out.println("Der Pfad kann also z.B. in der Form Deutsches Gedächtnis/Interviews Audio/ angegeben werden. Er muss mit einem / enden.");
        System.out.println("In der Windows-Eingabeauffoderung sollte zudem der Befehl 'chcp 1252' eingegeben werden, um sicherzustellen, dass Umlaute richtig erkannt werden." );
        String path = in.nextLine();
        FileHarvester fileHarvester = new FileHarvester();
        OOFileManipulator fileManipulator = new OOFileManipulator();

        if (fileSystem.equals("l")) {
            File file = new File(path);
            System.out.println("Beginne mit Sammlung der Dateiinformationen...");
            fileHarvester.harvestFileInfo(file);
        }
        else if (fileSystem.equals("n")) {
            jcifs.Config.setProperty( "jcifs.smb.lmCompatibility", "3");
            String rootPath="smb://fsfl.fernuni-hagen.de/fl-groups$/" + path;
            System.out.println("Bitte geben Sie Ihren User-Namen ein: ");
            String user = in.nextLine();
            System.out.println("Bitte geben Sie Ihr Passwort ein: ");
            String pass = in.nextLine();
            NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("",user, pass);
            SmbFile smbFile = new SmbFile(rootPath,auth);
            System.out.println("Der zu untersuchende Pfad lautet " + rootPath);
            if(smbFile.canRead()) System.out.println("Zugriff auf Netzwerk erfolgreich!");
            System.out.println("Zugriffsrechte: " + smbFile.getPermission());
            System.out.println("Beginne mit Sammlung der Dateiinformationen...");
            fileHarvester.harvestFileInfo(smbFile);
        }
        fileManipulator.writeToSheet(fileHarvester.getFileInfoList());
        in.close();

    }
}
