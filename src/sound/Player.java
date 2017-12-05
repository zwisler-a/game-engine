package sound;

import common.Logger.Logger;

import javax.sound.sampled.*;
import java.io.File;

public class Player {
    public static Clip load(String path) {
        try {
            File file = new File(path);
            AudioInputStream stream;
            AudioFormat format;
            DataLine.Info info;
            Clip clip;

            stream = AudioSystem.getAudioInputStream(file);
            format = stream.getFormat();
            info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
            return clip;
        } catch (Exception e) {
            Logger.error(e.toString());
            e.printStackTrace();
        }
        return null;

    }
}
