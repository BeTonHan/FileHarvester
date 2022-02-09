package FileHarvester;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class QuickHasher {

    // this method gives a NoSuchAlgorithmException in case
    // we pass a string which dosen't have any hashing
    // algorithm in its correspondence

    public String createHash(String path, boolean isLocalFile)
            throws IOException, NoSuchAlgorithmException
    {

        // create a file object referencing any file from
        // the system of which checksum is to be generated
        File file = new File(path);

        // instantiate a MessageDigest Object by passing
        // string "MD5" this means that this object will use
        // MD5 hashing algorithm to generate the checksum
        MessageDigest mdigest = MessageDigest.getInstance("MD5");

        // Get the checksum
        String checksum = checksum(mdigest, file);

        //Falls path eine kopierte Datei ist, wird diese gelöscht
        if (!isLocalFile) {
            file.delete();
            System.out.println("Datei " + file.getName() + " gelöscht.");
        }

        // print out the checksum
        return checksum;
    }

    // this method return the complete  hash of the file
    // passed
    private static String checksum(MessageDigest digest,
                                   File file)
            throws IOException
    {
        // Get file input stream for reading the file
        // content
        FileInputStream fis = new FileInputStream(file);

        // Create byte array to read data in chunks
        byte[] byteArray = new byte[1024];
        int bytesCount = 0;

        // read the data from file and update that data in
        // the message digest
        while ((bytesCount = fis.read(byteArray)) != -1)
        {
            digest.update(byteArray, 0, bytesCount);
        };

        // close the input stream
        fis.close();

        // store the bytes returned by the digest() method
        byte[] bytes = digest.digest();

        //  converts bytes from decimal to hexadecimal format

        StringBuilder sb = new StringBuilder();

        // loop through the bytes array
        for (int i = 0; i < bytes.length; i++) {

            sb.append(Integer
                    .toString((bytes[i] & 0xff) + 0x100, 16)
                    .substring(1));
        }

        // returns the complete hash
        return sb.toString();
    }
}