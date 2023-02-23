import Phaser from "phaser";
import axios from "axios";

export default class Talk extends Phaser.Physics.Arcade.Image {

    constructor(scene) {
        super(scene, 450, 450, "talk");
        this.scale = 1;
        this.alpha = 1;

        this.t_content = scene.add.text(-500,-500,"def").setVisible(false)

        scene.add.existing(this);
        this.setInteractive();
        //scene.input.setDraggable(this)
        //this.on('pointerup', this.popup, scene);


        this.on('pointerdown', function(pointer) {
            scene.viewTalk(this.t_content.text)
            console.log("!!!")
            console.log(this.t_content.text)

        });
    }

}
