package asd.sort.KWaySort;

import asd.sort.Element;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;



public class Source implements Comparable {
    private static final Logger logger = LogManager.getLogger();
    private String filePath;
    private File file;
    private Element currentElement;
    private BufferedReader reader;
    private String currentLine;

    public Source(String filePath) throws IOException {
        this.filePath = filePath;
        file = new File(filePath);
        if(!file.exists())
            throw new RuntimeException("File does not exists -> " + file.getAbsolutePath());
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        read();
    }

    public Element pickElement(){
        return currentElement;
    }

    public boolean pickNextElement() throws IOException {
        if(read())
            return true;
        else
            return false;
    }

    private boolean read() throws IOException {

        currentLine = reader.readLine();
        if (currentLine!=null) {
            currentElement = new Element(currentLine);
            logger.trace("Sorce updated -> " + this.toString());
            return true;
        }else {
            reader.close();
            return false;
        }
    }

    @Override
    public int compareTo(Object o) {
        if(((Source)o).getCurrentElement().getValue() < this.currentElement.getValue())
            return 1;
        else if (((Source)o).getCurrentElement().getValue() > this.currentElement.getValue())
            return -1;
        else
            return 0;
    }

    @Override
    public String toString() {
        return "Source{" +
                "filePath='" + filePath + '\'' +
                ", currentLine='" + currentLine + '\'' +
                '}';
    }

    public Element getCurrentElement() {
        return currentElement;
    }

    public String getFilePath() {
        return filePath;
    }
}
