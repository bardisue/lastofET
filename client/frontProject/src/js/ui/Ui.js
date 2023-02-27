import Phaser from "phaser";

export const Direction = Object.freeze({
    Up: 'Up',
    Down: 'Down',
    Left: 'Left',
    Right: 'Right'
});
export default class Ui extends Phaser.Physics.Arcade.Image {
    constructor(scene) {

      super(scene, 0, 0, "UI");
      this.scale = 3.5;
      this.alpha = 1;
      scene.add.existing(this);
      scene.physics.add.existing(this);
      this.setPosition(scene.cameras.main.centerX,scene.cameras.main.centerY+220)
      this.setScrollFactor(0,0)


      this.talk = scene.physics.add.sprite(0,0, "talk")
        this.talk.scale =0.8
      this.talk.setPosition(scene.cameras.main.centerX,scene.cameras.main.centerY+220);
      this.talk.setScrollFactor(0,0);
      this.talk.setScale(3);


      this.button = scene.physics.add.sprite(0,0, "talk").setName("talkSprite");
        this.button.scale =0.8
      this.button.setPosition(scene.cameras.main.centerX,scene.cameras.main.centerY+220);
      this.button.setInteractive({ useHandCursor: true });
      this.button.setScrollFactor(0,0);
      this.button.setScale(3);

      this.photo = scene.physics.add.sprite(0,0, "photo")
      this.photo.setPosition(scene.cameras.main.centerX-120,scene.cameras.main.centerY+220);
      this.photo.setScrollFactor(0,0);
      this.photo.setScale(0.15);

      this.photoButton = scene.physics.add.sprite(0,0, "photo").setName("photoSprite");
      this.photoButton.setPosition(scene.cameras.main.centerX-120,scene.cameras.main.centerY+220);
      this.photoButton.setInteractive({ useHandCursor: true });
      this.photoButton.setScrollFactor(0,0);
      this.photoButton.setScale(0.15);


      scene.input.setDraggable(this.button);
        scene.input.setDraggable(this.photoButton);

      scene.input.on('drag', function(pointer, gameObject, dragX, dragY) {
          gameObject.x = dragX;
          gameObject.y = dragY;
      });
      let arr = [];
      let count = 0;
      /***
      for(let i = 0; i < 500; i++) {
          arr.push(new Talk(scene).setPosition(-59 , -50));
      }
       ***/

      scene.input.on('dragend', function(pointer, gameObject) {
          console.log(scene.input.x, scene.input.y)
          if(gameObject.name === "photoSprite"){
              gameObject.setPosition(scene.cameras.main.centerX-120,scene.cameras.main.centerY+220);
              scene.postPhoto(pointer.worldX, pointer.worldY);
          }
          else if(gameObject.name === "talkSprite"){
              gameObject.setPosition(scene.cameras.main.centerX,scene.cameras.main.centerY+220);
              scene.postTalk(pointer.worldX, pointer.worldY);
          }

      });
    }
}
