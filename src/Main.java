import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    static void saveGame(String savingPath, GameProgress currentProgress) {
        try
                (FileOutputStream fos = new FileOutputStream(savingPath);
                 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(currentProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    static void zipFiles(String zipPath, String[] array) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipPath))) {
            for (String filePath : array) {
                try (FileInputStream fis = new FileInputStream(filePath)) {
                    ZipEntry entry = new ZipEntry("packed_"+filePath);
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    static void deleteFile(String[] array) {
        for (String s : array) {
            File dir = new File (s);
            if (dir.delete()) System.out.println(dir.toString()+" удален");
        }

    }

    public static void main(String[] args) {

        GameProgress save1 = new GameProgress(100, 2, 21, 10000);
        GameProgress save2 = new GameProgress(96, 3, 24, 9877);
        GameProgress save3 = new GameProgress(90, 1, 19, 8001);

        saveGame("D://Games/savegames/save1.dat", save1);
        saveGame("D://Games/savegames/save2.dat", save2);
        saveGame("D://Games/savegames/save3.dat", save3);


        zipFiles("D://Games/savegames/zip1.zip", new String[]{"D://Games/savegames/save1.dat", "D://Games/savegames/save2.dat", "D://Games/savegames/save3.dat"});

       deleteFile(new String[]{"D://Games/savegames/save1.dat", "D://Games/savegames/save2.dat", "D://Games/savegames/save3.dat"});
    }
}