package FileHarvester;


import java.io.*;
import java.util.Collections;
import java.util.ArrayList;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.aarboard.nextcloud.api.NextcloudConnector;
import java.util.List;
import jcifs.smb.SmbFile;
import org.apache.commons.io.FileUtils;

//Traverses a given folder on a local file system or a nextcloud server and creates a list of filename, path and checksum.
public class FileHarvester{

    ArrayList<FileInfo> fileInfoList = new ArrayList<FileInfo>();
    MessageDigest md = MessageDigest.getInstance("SHA-256");
    QuickHasher hash = new QuickHasher();

    public FileHarvester() throws NoSuchAlgorithmException {
    }

    //@param rootFolder The local path on the file system to be examined.
    public void harvestFileInfo(File rootFolder) throws IOException, NoSuchAlgorithmException {
        //Traverses folder structure recursively
        if (rootFolder.isDirectory()) {
            for (String fileName : rootFolder.list()) {
                harvestFileInfo(new File(rootFolder, fileName));
            }
        }
        else {
            String fileName = rootFolder.getName();
            String path = rootFolder.getAbsolutePath();
            String checksum = hash.createHash(path, true);
            fileInfoList.add(new FileInfo(path, fileName, checksum));
            System.out.println("Collected checksum and path for file " + path + ".");
        }
    }

    public void harvestFileInfo(SmbFile smbFile) throws IOException, NoSuchAlgorithmException {
        //Traverses folder structure recursively
        if (smbFile.isDirectory()) {
            for (SmbFile fileName : smbFile.listFiles()) {
                harvestFileInfo(fileName);
            }
        }
        else {
            String fileName = smbFile.getName();
            String path = smbFile.getPath();
            downloadFile(smbFile);
            String checksum = hash.createHash(smbFile.getName(), false);
            fileInfoList.add(new FileInfo(path, fileName, checksum));
            System.out.println("Collected checksum and path for file " + path + ".");
        }
    }

    //Method that traverses Nextcloud-Filesystem just like the method above does for a local file system.
    //@param fileName Name of file on Nextcloud
    //@param nxtInstance Object that points to the Nextcloud-instance
    //@param rootFolder Path of the Nextcloud-instance
    public void harvestFileInfo(String fileName, NextcloudConnector nxtInstance, String rootFolder) throws IOException, NoSuchAlgorithmException {
        //Check for directory with regular expression
        if (!fileName.matches("^.*\\..{2,4}$")) {
        //if (nxtInstance.folderExists(fileName)) {
            List <String> folderContent = nxtInstance.listFolderContent(rootFolder);

            System.out.println("Der aktuell betrachtete Ordner enth??lt folgende Dateien und Verzeichnisse: " + folderContent);
            //System.out.println(fileName + " ist ein Verzeichnis. Rufe Methode auf mit " + fileName + " und dem Pfad " + rootFolder );
            for (int i=1; i< folderContent.size(); i++) {
                fileName = folderContent.get(i);
                //Rekursiver Aufruf f??r Ordner
                if (!fileName.matches("^.*\\..{2,4}$")) {
                    //if fileName is a folder, the rootFolder-path is adjusted accordingly.
                    harvestFileInfo(fileName, nxtInstance, rootFolder + "/" + folderContent.get(i));
                }
                //Rekursiver Aufruf f??r Dateien
                else {
                    harvestFileInfo(fileName, nxtInstance, rootFolder);
                }
            }
        }
        else {
            if (!fileName.equals("FileInfo.ods")) {
                System.out.println("Lade Datei " + rootFolder + "/" + fileName + " herunter.");
                nxtInstance.downloadFile(rootFolder + "/" + fileName, System.getProperty("user.dir"));
                String checksum = hash.createHash(fileName, false);
                fileInfoList.add(new FileInfo(rootFolder, fileName, checksum));
            }
        }

    }

    public void harvestNextCloud(String serverAdress, String user, String password, String path) throws IOException, NoSuchAlgorithmException {
        List<String> currentFolderContent;
        NextcloudConnector nxtCldInstance = new NextcloudConnector(serverAdress, user, password);
        currentFolderContent = nxtCldInstance.listFolderContent(path);
        //Read out root folder content
        harvestFileInfo("", nxtCldInstance, path);
    }

    private void downloadFile(SmbFile file) throws IOException, NoSuchAlgorithmException {
        // L??dt Datei herunter
        InputStream initialStream = file.getInputStream();
        File targetFile = new File(file.getName());
        FileUtils.copyInputStreamToFile(initialStream, targetFile);
        System.out.println("Datei " + file.getPath() + " erfolgreich kopiert!");
    }


    public ArrayList<FileInfo> getSortedFileList(){
        ArrayList<FileInfo> sortedList = new ArrayList<FileInfo>();
        for (int i = 0; i<fileInfoList.size(); i++) sortedList.add(fileInfoList.get(i));
        Collections.sort(sortedList);
        return sortedList;
    }
    public ArrayList<FileInfo> getFileInfoList() {
        return fileInfoList;
    }
}
