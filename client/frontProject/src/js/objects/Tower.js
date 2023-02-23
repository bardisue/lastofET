
export default class Tower extends Phaser.Physics.Arcade.Image {

    constructor(scene) {
        super(scene, 800, 480, "empty");
        this.scale = 1;
        this.alpha = 1;


        scene.add.existing(this);
        scene.physics.add.existing(this);

        this.setInteractive();
        //scene.input.setDraggable(this)
        //this.on('pointerup', this.popup, scene);


        this.on('pointerdown', function(pointer) {
            scene.viewBuilding()
            console.log("???")
        });


    }
}
