package FileHarvester;

import java.util.ArrayList;

public class MetaFileMatch {
    int id;
    ArrayList<String> fileName = new ArrayList<String>();
    ArrayList<String> path = new ArrayList<String>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<String> getFileName() {
        return fileName;
    }

    public void addFileName(String fileName) {
        this.fileName.add(fileName);
    }

    public ArrayList<String> getPath() {
        return path;
    }

    public void addPath(String path) {
        this.path.add(path);
    }
}
