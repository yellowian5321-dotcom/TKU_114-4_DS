
abstract class MediaFile {

    protected String fileName;
    protected long sizeBytes;

    public MediaFile(String fileName, long sizeBytes) {
        this.fileName = fileName;
        this.sizeBytes = sizeBytes;
    }

    public String getFileName() {
        return fileName;
    }
}

interface Playable {

    void play();
}

interface CompressibleMedia {

    void compress();
}

class ImageFile extends MediaFile implements CompressibleMedia {

    public ImageFile(String fileName, long sizeBytes) {
        super(fileName, sizeBytes);
    }

    @Override
    public void compress() {
        System.out.println("影像 [" + fileName + "] 已套用 WebP 無損壓縮。");
    }
}

class AudioFile extends MediaFile implements Playable, CompressibleMedia {

    public AudioFile(String fileName, long sizeBytes) {
        super(fileName, sizeBytes);
    }

    @Override
    public void play() {
        System.out.println("正在播放音訊串流: " + fileName);
    }

    @Override
    public void compress() {
        System.out.println("音訊 [" + fileName + "] 已壓縮至 AAC 格式。");
    }
}

class VideoFile extends MediaFile implements Playable {

    public VideoFile(String fileName, long sizeBytes) {
        super(fileName, sizeBytes);
    }

    @Override
    public void play() {
        System.out.println("正在解碼並播放 4K 影片: " + fileName);
    }
}

public class MediaProcessingSystem {

    public static void main(String[] args) {
        MediaFile[] library = new MediaFile[]{
            new ImageFile("banner.png", 2048),
            new AudioFile("bgm.wav", 15360),
            new VideoFile("intro.mp4", 512000)
        };

        for (MediaFile file : library) {
            System.out.println("處理檔案: " + file.getFileName());
            if (file instanceof Playable p) {
                p.play();
            }
            if (file instanceof CompressibleMedia c) {
                c.compress();
            }
            System.out.println("----------------------------------------");
        }
    }
}
