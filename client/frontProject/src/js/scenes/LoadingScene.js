import Phaser from 'phaser';
import bgImg from '../../assets/images/map.png';
import catImg from '../../assets/images/bus.png';
import talkImg from '../../assets/images/talk.png';
import uiImage from "../../assets/images/UI.png"
import photoImage from "../../assets/images/photo.png"
import emptyImage from "../../assets/images/empty.png"
import center2Img from "../../assets/images/center2.png"


export default class LoadingScene extends Phaser.Scene{
    constructor() {
        super("bootGame");
        // bootGame : 이 scene의 identifier
    }
    //http://labs.phaser.io/assets/fonts/bitmap/hyperdrive.png
    preload() {
        this.load.image("photo", photoImage);
        this.load.image("UI", uiImage);
        this.load.image("background", bgImg);
        this.load.image("cat", catImg);
        this.load.image("talk", talkImg);
        this.load.image("empty", emptyImage)
        this.load.image("center2", center2Img)
    }

    create() {
        this.add.text(20, 20, "Loading game...");
        this.scene.start("playGame");

    }
}
