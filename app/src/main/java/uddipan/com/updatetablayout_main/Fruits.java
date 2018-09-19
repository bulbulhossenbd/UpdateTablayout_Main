package uddipan.com.updatetablayout_main;

/**
 * Created by Kamere on 4/12/2018.
 */

public class Fruits {
    String name,desc;
    int image;

    public Fruits(String name, String desc, int image) {
        this.name = name;
        this.desc = desc;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public int getImage() {
        return image;
    }
}
