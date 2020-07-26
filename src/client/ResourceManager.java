package client;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ResourceManager {





    private ClientConstants clientConstants;
    private HashMap<String, BufferedImage> imageMap = new HashMap<>();

    public ResourceManager() {
        clientConstants= new ClientConstants();
        loadImages();
        System.out.println("finish loading resources");
    }



    public ClientConstants getClientConstants() {
        return clientConstants;
    }

    public void setClientConstants(ClientConstants clientConstants) {
        this.clientConstants = clientConstants;
    }

    public HashMap<String, BufferedImage> getImageMap() {
        return imageMap;
    }

    public void setImageMap(HashMap<String, BufferedImage> imageMap) {
        this.imageMap = imageMap;
    }

    public BufferedImage getImage(String imageName){return imageMap.get(imageName);}

    private void loadImages() {
        imageMap.put("gun",find("gun"));
        imageMap.put("gunsNroses",find("gunsNroses"));
        imageMap.put("rose",find("rose"));
        imageMap.put("skelet",find("skelet"));
    }

    private BufferedImage find(String name) {
        String url = String.format("resources/image/%s.jpg", name);
        BufferedImage image = null;
        try {
            File file = new File(url);
            image = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
