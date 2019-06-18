package gui.states;

import logic.environment.manager.file.ReadXmlFile;
import logic.environment.manager.menu.Menu;
import network.client.Client;
import network.server.GameStates;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import java.util.ArrayList;

public class MultiplayerState extends BasicInvaderState {

    //DIMENSIONS
    private double maxWidth;
    private double bulletSize;
    private double brickSize;
    private double invaderSize;
    private double shipSize;

    private ArrayList<String[]> invaderInfos;
    private ArrayList<String[]> invaderBulletInfos;
    private ArrayList<String[]> brickInfos;
    private ArrayList<String[]> spaceShipInfos;
    private ArrayList<String[]> spaceShipBulletInfos;
    private String scoreInfos;
    private GameStates gameStates;

    private UnicodeFont uniFontData;

    //IMAGES
    private Image background;
    private Image invaderImage;
    private Image spaceShipImage;
    private ArrayList<Image> brickImages = new ArrayList<>();
    private Image bulletImage;

    private Menu menu;
    private static Client client;

    public MultiplayerState(Menu menu) {
        this.menu = menu;
        invaderInfos = new ArrayList<>();
        invaderBulletInfos = new ArrayList<>();
        brickInfos = new ArrayList<>();
        spaceShipInfos = new ArrayList<>();
        spaceShipBulletInfos = new ArrayList<>();

        maxWidth = menu.getMaxWidth();
        shipSize = maxWidth / 20;
        bulletSize = maxWidth / 60;
        brickSize = maxWidth / 40;
        invaderSize = maxWidth / 20;

        try {
            invaderImage = new Image(ReadXmlFile.read("defaultInvader"));
            bulletImage = new Image(ReadXmlFile.read("defaultBullet"));
            for (int i = 0; i < 4; i++) {
                brickImages.add(new Image(ReadXmlFile.read("brick" + i)));
            }
            spaceShipImage = new Image(ReadXmlFile.read("ship0"));
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        background = new Image(ReadXmlFile.read("defaultBackground"));
        uniFontData = build(3 * gameContainer.getWidth() / 100f);
    }

    public static void setClient(Client client){
        MultiplayerState.client = client;
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.drawImage(background, 0, 0);

        getString();

        uniFontData.drawString(85 * gameContainer.getWidth() / 100f, 2 * gameContainer.getHeight() / 100f,
                "Lives: " + spaceShipInfos.get(client.getID())[2], Color.red);
        uniFontData.drawString((gameContainer.getWidth() - uniFontData.getWidth("Score: ")) / 2,
                2 * gameContainer.getHeight() / 100f, "Score: " + scoreInfos, Color.white);

        for (String[] string : invaderInfos) {
            invaderImage.draw(Float.parseFloat(string[0]), Float.parseFloat(string[1]),
                    (float) invaderSize, (float) invaderSize);
        }

        for (String[] string : invaderBulletInfos) {
            bulletImage.draw(Float.parseFloat(string[0]), Float.parseFloat(string[1]),
                    (float) bulletSize, (float) bulletSize);
        }

        for (String[] string : brickInfos) {
            brickImages.get(4 - Integer.parseInt(string[2])).draw(Float.parseFloat(string[0]),
                    Float.parseFloat(string[1]), (float) brickSize, (float) brickSize);
        }

        for (String[] string : spaceShipInfos) {
            spaceShipImage.draw(Float.parseFloat(string[0]), Float.parseFloat(string[1]),
                    (float) shipSize, (float) shipSize);
        }

        for (String[] string : spaceShipBulletInfos) {
            bulletImage.draw(Float.parseFloat(string[0]), Float.parseFloat(string[1]),
                    (float) bulletSize, (float) bulletSize);
        }

    }

    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
        Input input = gameContainer.getInput();

        if (input.isKeyDown(Input.KEY_ESCAPE)) {
            client.close();
            stateBasedGame.enterState(1, new FadeOutTransition(), new FadeInTransition());
        }
    }

    private void getString(){
        String []rcvdata = client.getRcvdata();

        for(String strings : rcvdata[0].split("\\t")) {
            if(!strings.equals("")) {
                invaderInfos.add(strings.split("_"));
            }
        }

        for(String strings : rcvdata[1].split("\\t")) {
            if(!strings.equals("")) {
                invaderBulletInfos.add(strings.split("_"));
            }
        }
        for(String strings : rcvdata[2].split("\\t")) {
            if(!strings.equals("")) {
                brickInfos.add(strings.split("_"));
            }
        }
        for(String strings : rcvdata[3].split("\\t")) {
            if(!strings.equals("")) {
                spaceShipInfos.add(strings.split("_"));
            }
        }
        for(String strings : rcvdata[4].split("\\t")) {
            if(!strings.equals("")) {
                spaceShipBulletInfos.add(strings.split("_"));
            }
        }
        scoreInfos = rcvdata[5];
    }

    @Override
    public int getID() {
        return 9;
    }
}
