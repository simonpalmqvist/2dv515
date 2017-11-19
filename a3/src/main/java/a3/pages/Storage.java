package a3.pages;

import java.io.*;

public class Storage {

    private String storageName;

    public Storage(String storageName) {
        this.storageName = storageName + ".dat";
    }

    public void save (Object objectToStore){
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(storageName));
            outputStream.writeObject(objectToStore);
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object read () throws Exception {
        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(storageName));
        Object result = inputStream.readObject();
        inputStream.close();

        return result;
    }

    public boolean exists() {
        File file = new File(storageName);
        return file.isFile() && file.canRead();
    }
}
