package client;

public class ClientConstants {

    private int panelHeight=770;
    private int panelWidth=700;
    private int networkPanelMinx=100;
    private int networkPanelMinY=350;
    private int networkPanelSizey=20;
    private int networkPanelSizeX=50;
    private int port=8000;
    private int sizeBoard=250;

    public ClientConstants() {

    }


    //getter&setter


    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getNetworkPanelMinx() {
        return networkPanelMinx;
    }

    public void setNetworkPanelMinx(int networkPanelMinx) {
        this.networkPanelMinx = networkPanelMinx;
    }

    public int getNetworkPanelMinY() {
        return networkPanelMinY;
    }

    public void setNetworkPanelMinY(int networkPanelMinY) {
        this.networkPanelMinY = networkPanelMinY;
    }

    public int getNetworkPanelSizey() {
        return networkPanelSizey;
    }

    public void setNetworkPanelSizey(int networkPanelSizey) {
        this.networkPanelSizey = networkPanelSizey;
    }

    public int getNetworkPanelSizeX() {
        return networkPanelSizeX;
    }

    public void setNetworkPanelSizeX(int networkPanelSizeX) {
        this.networkPanelSizeX = networkPanelSizeX;
    }

    public int getPanelHeight() {
        return panelHeight;
    }

    public void setPanelHeight(int panelHeight) {
        this.panelHeight = panelHeight;
    }

    public int getPanelWidth() {
        return panelWidth;
    }

    public void setPanelWidth(int panelWidth) {
        this.panelWidth = panelWidth;
    }

    public int getSizeBoard() {
        return sizeBoard;
    }

    public void setSizeBoard(int sizeBoard) {
        this.sizeBoard = sizeBoard;
    }
}
