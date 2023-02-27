import Phaser from 'phaser';
import Config from "../Config";
import Player, {Direction} from '../characters/Player';
import Structure from '../objects/Tower';
import axios from "axios";
import Talk from "../marker/Talk";
import Photo from "../marker/Photo";
import Ui from "../ui/Ui";


export default class PlayingScene extends Phaser.Scene {

   // triggerTimer = phaser.Time.TimerEvent;
    constructor() {
        super("playGame");
    }

    preload(config)
    {
        this.load.html("please", "/assets/text/image.html")
        this.load.html("signUp", "/assets/text/signUp.html")
        this.load.html("talkHtml", "/assets/text/talkHtml.html")
        this.load.html('nameform', '/assets/text/nameform.html')
        this.load.html('talkViewHtml', '/assets/text/talkViewHtml.html')
        this.load.html('photoPostHtml', '/assets/text/photoPost.html')
        this.load.html('photoGetHtml', '/assets/text/getPhoto.html')
        this.load.html('buildingHtml', '/assets/text/building.html')
    }
    create(config) {



        this.getNicknameCount = 0
        this.resetCharacterCount = 0
        this.startPostPosition = 0

        this.created = false;

        this.talkList = []
        this.photoList = []
        this.lostList = []
        this.counter = 0

        this.changedNick = "";



       // this.m_element = this.add.dom(400, 0).createFromCache('nameform');

        this.playList = []
        // sound
        this.sound.pauseOnBlur = false;
        // background
        this.m_background = this.add.tileSprite(0, 0, 1980, 4000, "background");
        this.m_background.setOrigin(0, 0);
        //minimap


        this.runLogin();


        this.m_login = false;

        // player
        //this.m_player = new Player(this);
        this.m_player = new Player(this);
        //this.tmp_photo = new Photo(this);
        //this.tmp_photo.setPosition(200 ,200)
        this.m_player.setName("20000000")
            //this.resetCharacter();

        this.m_canMove = true;

        this.cameras.main.setBounds(0, 0, 1920, 3000);
        //this.m_player.add(new Player(this));
        this.cameras.main.startFollow(this.m_player);
        //this.cameras.main.setSize(200,200);
        this.cameras.main.setZoom(2,2);


        //this.minimap = this.cameras.add(900, 900, 500, 500).setZoom(0.2).setName('mini');
        // this.minimap.setBackgroundColor(0x002244);
        //  this.minimap.scrollX = 1600;
        // this.minimap.scrollY = 300;

        //this.cameras.main.setSize(3000,2000)
        //this.cameras.resize(200 ,200);
        //this.cameras.onResize(200,200);
        // keys
        this.keyLeft = this.input.keyboard.addKey(Phaser.Input.Keyboard.KeyCodes.LEFT);
        this.keyDown = this.input.keyboard.addKey(Phaser.Input.Keyboard.KeyCodes.DOWN);
        this.keyRight = this.input.keyboard.addKey(Phaser.Input.Keyboard.KeyCodes.RIGHT);
        this.keyUp = this.input.keyboard.addKey(Phaser.Input.Keyboard.KeyCodes.UP);
        ////console.log(this.m_cursorKeys);

        //this.m_talk = new Talk(this);
        //Object
        /***
        this.m_click = this.add.sprite(400, 300, "bus").setInteractive()//on('pointerdown', this.popup);

        this.m_click.on('pointerup', this.popup, this);

        this.m_text = this.add.bitmapText(this.m_player.getCenter().x, this.m_player.getCenter().y, 'hyper', 'Arkanoid\nRevenge of Doh', 96);
        this.m_text.setText("YES");
        const graphics = this.add.graphics(0, 0);
        const bounds = this.m_text.getTextBounds();

        graphics.lineStyle(1, 0x00FF00, 1.0);
        graphics.strokeRect(bounds.global.x, bounds.global.y, bounds.global.width, bounds.global.height);
         ***/


        //this.m_tower.body.onWorldBounds = true;
        //this.m_tower.body.setCollideWorldBounds(false );


        this.resources = 0;
        this.timer = 0;

        this.created = false;

        this.loadObjects();

        //const text = this.add.dom(400, 400, "span", { color: "white" }, "Hello");


        /***
        var element =  this.add.dom(330, 330).createFromCache('please').setOrigin(0);
        element.setScale(0.5, 0.5)
        //this.m_element.setVisible(true);
        element.setPerspective(800);
       // let photo = this.m_element.getChildByID('Frame');
         ***/

        //UI
        this.m_ui = new Ui(this);
       // this.m_ui.setVisible(false);

       // this.m_ui.talk.setPosition(this.cameras.main.centerX-100,this.cameras.main.centerY+215);
       // this.m_ui.setPosition(this.m_player.getCenter().x,this.m_player.getCenter().y+81 )
        //this.m_ui.startFollow(this.m_player);
        //this.m_ui.setPosition(this.cameras.main.centerX,this.cameras.main.centerY+181)


        this.m_structure = new Structure(this).setPosition(420, 470).setImmovable(true).setScale(2.4,0.9).setVisible(true).setOrigin(0, 0);
        this.physics.add.collider(this.m_player, this.m_structure);


        ////console.log(photo)
        /***
        axios.post('http://localhost:3002/upload' ,{
            "nickname" : this.m_player.name,
            "point" : {
                "x" : this.m_player.getCenter().x,
                "y" : this.m_player.getCenter().y
            }
        },{
            withCredentials: true,
        }).then(res => {
            //console.log('succes');
        }).catch(error => {
            //console.log('erro', error);
        })
        ***/
        this.buildingLayer = this.add.layer();
        this.buildingLayer.add(this.m_structure.setVisible(false))
        this.markerLayer = this.add.layer();

        this.playerLayer = this.add.layer();

        this.uiLayer = this.add.layer();
        this.m_ui.photoButton.setVisible(false)
        this.m_ui.button.setVisible(false)
        this.uiLayer.add(this.m_ui)
        this.uiLayer.add(this.m_ui.talk)
        this.uiLayer.add(this.m_ui.button)
        this.uiLayer.add(this.m_ui.photoButton)
        this.uiLayer.add(this.m_ui.photo)
    }

    viewTalk(t_text){
        let title = ""
        let content = ""
        axios.post('api/marker/read' ,{
            "id" : t_text,
            "type" : "Talk"
        },{
            withCredentials: true,
        }).then(res => {
            let tmp = JSON.stringify(res.data)
            let myObj = JSON.parse(tmp);
            title = myObj.title
            content = myObj.contents
            let talkView = this.add.dom(1000, 700).createFromCache('talkViewHtml').setScrollFactor(0)

            talkView.setScale(0.6)

            talkView.setPerspective(800);

            talkView.getChildByName('content').value = content
            talkView.getChildByName('showTitle').value = title

            talkView.addListener('click');

            talkView.on('click', function (event) {
                if (event.target.name === 'closeButton') {
                    //console.log("close")
                    talkView.setVisible(false);
                }
            });
        }).catch(error => {
            //console.log('erro', error);
        })

    }

    postTalk(x,y){
        let element =  this.add.dom(1000, 700).createFromCache('talkHtml').setScrollFactor(0);


        element.setScale(0.6)

        element.setPerspective(800);


        let post_id = this.m_id.text

        //console.log(post_id)
        let post_token = this.m_token;


        element.addListener('click');

        element.on('click', function (event) {
            if (event.target.name === 'submitButton')
            {
                ////console.log(post_id)
                //console.log(this.getChildByName('title'))
                let title = this.getChildByName('title').value;
                let content = this.getChildByName('content').value;
                ////console.log(obj.getCenter().x,obj.getCenter().y, post_id)

                axios.post('api/marker/new/talk' ,{
                    "user_id" : post_id,
                    "point" : {
                        "x" : x,
                        "y" : y
                    },
                    "title" : title,
                    "contents" : content
                },{
                    headers:{
                        Authorization: post_token.text
                    },
                    withCredentials: true,
                }).then(res => {
                    element.setVisible(false);
                    //console.log("success post talk")
                    //obj.setName(post_id)
                }).catch(error => {
                    //console.log(post_token.text)
                })
            }
            else if (event.target.name === 'closeButton'){
                element.setVisible(false);
            }
        });

    }

    viewPhoto(t_text){
        let title = ""
        let content = ""
        let srcGet = ""
        axios.post('api/marker/read' ,{
            "id" : t_text,
            "type" : "Photo"
        },{
            withCredentials: true,
        }).then(res => {
            let tmp = JSON.stringify(res.data)
            let myObj = JSON.parse(tmp);
            title = myObj.title
            content = myObj.contents
            srcGet = myObj.url

            let element =  this.add.dom(1000, 700).createFromCache('photoGetHtml').setScrollFactor(0);

            element.setScale(0.6)
            element.setPerspective(800);

            element.addListener('click');

            let post_id = this.m_id.text


            element.getChildByID('imageId').src = srcGet
            element.getChildByName('title').value = title
            //element.getChildByName('imageId').src = "http://localhost:3002/use/booo.png"

            //element.getChildByName('uploadName').value = this.m_id.text+today.toLocaleString()

            ////console.log

            element.on('click', function (event) {
                if (event.target.name === 'closeButton') {
                    element.setVisible(false);
                }
            });
        }).catch(error => {
            //console.log('erro', error);
        })

    }

    postPhoto(x,y){

        let post_token = this.m_token;

        let element =  this.add.dom(1000, 700).createFromCache('photoPostHtml').setScrollFactor(0);


        element.setScale(0.6)
        element.setPerspective(800);

        element.addListener('click');

        let post_id = this.m_id.text

        let today = new Date();

        let fileName = "x"+Math.floor(x)+"y"+Math.floor(y)

        element.getChildByName('uploadName').value = fileName
       // element.getChildByName('uploadName').value = "aaa"
        ////console.log

        element.on('click', function (event) {
            if (event.target.name === 'closeButton') {
                element.setVisible(false);
            }
            else if (event.target.name === 'submitButton'){
                let title = this.getChildByName('title').value;
                //let content = this.getChildByName('content').value;
                axios.post('api/marker/new/photo' ,{
                    "user_id" : post_id,
                    "point" : {
                        "x" : x,
                        "y" : y
                    },
                    "title" : title,
                    "contents" : "",
                    "url" : "imageserver/use/"+fileName+".png"
                },{
                    headers:{
                        Authorization: post_token.text
                    },
                    withCredentials: true,
                }).then(res => {
                    element.setVisible(false);
                    //console.log("success post talk")
                    //obj.setName(post_id)
                }).catch(error => {
                    //console.log(post_token.text)
                })
            }
        });
    }

    getPhoto(){

    }

    runLogin(){

        let promise;

        let login = this.m_login;

        let element = this.add.dom(500, 500).createFromCache('please').setOrigin(0);
        let element2 = this.add.dom(500, 400).createFromCache('signUp').setOrigin(0).setVisible(false);
        this.m_token = this.add.text(-100, -100, 'Please login to play', {
            color: 'white',
            fontFamily: 'Arial',
            fontSize: '32px '
        });
        this.m_id = this.add.text(-100, -100, 'noID', {
            color: 'white',
            fontFamily: 'Arial',
            fontSize: '32px '
        });

        let m_id = this.m_id;
        let m_token = this.m_token;
        //this.m_player.canMove = false;
        element.setScale(1, 1)
        //this.m_element.setVisible(true);
        element.setPerspective(800);

        // let photo = this.m_element.getChildByID('Frame');

        element.addListener('click');

        element.on('click', function (event) {
            if (event.target.name === 'loginButton') {
                let inputUsername = this.getChildByName('username').value;
                let inputPassword = this.getChildByName('password').value;
                //console.log(inputUsername, inputPassword)
                let authentication = false;
                axios.post('api/auth/login', {
                    "id": inputUsername,
                    "password": inputPassword
                }, {
                    withCredentials: true,
                }).then(res => {
                    let tmp = JSON.stringify(res.data)
                    let myObj = JSON.parse(tmp);
                    m_token.setText("Bearer " + myObj.accessToken)
                    m_id.setText(inputUsername);
                    //console.log(m_token.text);
                    this.removeListener('click');
                    login = true;
                    element.setVisible(false);
                }).catch(error => {
                    //console.log(m_toekn.text())
                })
            }
            else if (event.target.id === 'forgot'){
                element.setVisible(false);
                ///
                element2.setVisible(true)

                element2.setPerspective(800);


                element2.addListener('click');

                element2.on('click', function (event) {
                    if (event.target.name === 'signUpButton') {
                        let inputUsername2 = this.getChildByName('username').value;
                        let inputPassword2 = this.getChildByName('password').value;
                        let inputName = this.getChildByName('name').value;
                        let inputNickname = this.getChildByName('nickname').value;
                        //console.log(inputUsername, inputPassword)
                        axios.post('api/auth/signup', {
                            "id" : inputUsername2,
                            "name" : inputName,
                            "nickname" : inputNickname,
                            "password" : inputPassword2
                        }, {
                            withCredentials: true,
                        }).then(res => {
                            element2.setVisible(false);
                            element.setVisible(true);
                        }).catch(error => {
                            //console.log(m_toekn.text())
                        })
                    }
                    else if (event.target.id === 'forgot'){
                        element2.setVisible(false);
                        element.setVisible(true);
                    }
                });
            }
        });
        this.m_token.setText(m_token.text)

    }

    signUp(){

    }

    getNickname(){
        //console.log(this.m_token.text)
        axios('api/member/me' ,
            {
            headers: {
                Authorization: this.m_token.text
            }
            }).then(res => {
            this.m_player.name = JSON.parse(JSON.stringify(res.data)).nickname
            this.m_player.nickname.setText(JSON.parse(JSON.stringify(res.data)).nickname)
            //console.log("success get nick " +this.m_player.name)
        }).catch(error => {
            //console.log('WTF', error);
        });
    }


    popup(){
        let board = this.add.sprite(this.m_player.getCenter().x, this.m_player.getCenter().y, "board");
        /***
        let text = this.add.text(board.getTopLeft().x, board.getTopLeft().y, "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa").setOrigin(0);
        let graphics = this.make.graphics();
        graphics.fillRect(this.m_player.getCenter().x, this.m_player.getCenter().y, 205, 141);
        let mask = new Phaser.Display.Masks.GeometryMask(this, graphics);
        text.setMask(mask);
        //let zone = this.add.zone(board.getTopLeft(), board.getTopLeft(), 205, 141).setOrigin(0).setInteractive();

        this.m_container = this.add.container(board.x, board.y)
        this.m_container.content = container.scene.add.text(
            container.x - container.width / 2,
            container.y - container.height / 2,
            "", container.defaultStyles
        )
         ***/

        let graphics = this.make.graphics();

        // graphics.fillStyle(0xffffff);
        graphics.fillRect(board.getTopLeft().x, board.getTopLeft().y, 150, 150);

        var mask = new Phaser.Display.Masks.GeometryMask(this, graphics);

        var text = this.add.text(board.getTopLeft().x, board.getTopLeft().y, ["abc defgh ijklm nopq rsq uvwxyz", "abcdefghijklmnopqrsquvwxyz"], { fontFamily: 'Arial', color: '#00ff00', wordWrap: { width: 205 } }).setOrigin(0);

        text.setMask(mask);

        //  The rectangle they can 'drag' within
        var zone = this.add.zone(board.getTopLeft().x, board.getTopLeft().y, 150, 150).setOrigin(0).setInteractive();
    }

    loadObjects(){
         axios.get('api/buildings' ,
         {'Access-Control-Allow-Credentials': '*'},{
                withCredentials: true,
            }).then(res => {
             const tmp = JSON.stringify(res.data)
             const myObj = JSON.parse(tmp);
             let len = myObj.length;
             for(let i = 0; i <len; i++) {
                 this.m_tower = new Structure(this);
                 this.m_tower.setPosition(myObj[i].point.x,myObj[i].point.y);
                 this.m_tower.setImmovable(true);
                 this.physics.add.collider(this.m_player, this.m_tower);
             }
        }).catch(error => {
            //console.log('WTF', error);
        });
    }

    timerEvent(resources) {
        if(this.counter%5 !== 0){
            return
        }
        axios.defaults.withCredentials = true;
        axios.defaults.headers['Access-Control-Allow-Origin'] = '*';
        axios('api/player' ,
            {},{
            }).then(res => {
            this.treatData(res.data)
            this.postPosition();
        }).catch(error => {
            //console.log('erro', error);
        })

    }

    async postPosition(){
        let promise = new Promise((resolve, reject) => {
            setTimeout(() => resolve("완료!"), 1000)
        });
        let result = await promise; // 프라미스가 이행될 때까지 기다림 (*)
        axios.post('api/player/save' ,{
            "nickname" : this.m_player.name,
            "point" : {
                "x" : this.m_player.getCenter().x,
                "y" : this.m_player.getCenter().y
            }
        },{
            withCredentials: true,
        }).then(res => {
            //console.log('succes');
        }).catch(error => {
            //console.log('erro', error);
        })
    }

    resetCharacter(){
        axios.post('api/player/create_user' ,{
            "point" : {
                "x" : this.m_player.x,
                "y" : this.m_player.y,
            },
            "nickname" : this.m_player.name
        },{
            withCredentials: true,
        }).then(res => {
            const tmp = JSON.stringify(res.data);
            this.m_player.setName(JSON.parse(tmp).nickname);
            this.changedNick = this.m_player.name;
            //console.log('success');
            this.created = true;
        }).catch(error => {
            //console.log('erro', error);
        })
    }


    async treatData(res){
        const tmp = JSON.stringify(res)
        const myObj = JSON.parse(tmp);
        var len = myObj.length
        for(var i = 0; i <len; i++) {
            var tmpName = myObj[i].nickname
            var tmpX = myObj[i].point.x
            var tmpY = myObj[i].point.y
            if(tmpName === this.m_player.name) {
                continue;
            }
            var found = this.playList.find(element => element.name === tmpName);
            if(found === undefined){
                var nP = new Player(this);
                nP.setName(tmpName);
                nP.setPosition(tmpX,tmpY);
                nP.nickname.setText(tmpName)
                this.playerLayer.add(nP);
                this.playList.push(nP);
            }
            else{

                if (found.getCenter().x !== tmpX || found.getCenter().y !== tmpY){
                   // let target = new Phaser.Math.Vector2(tmpX,tmpY);
                   // let target = new Phaser.Math.Vector2(tmpX,tmpY);
                    //console.log(this.target.x, this.target.y)
                    //this.physics.moveToObject(found, target, 200);
                    //found.body.reset(tmpX, tmpY)
                    found.nickname.setPosition(tmpX-10, tmpY-30)
                    found.setPosition(tmpX,tmpY)
                }
            }
            /***
            if(!this.playList.){
                var nP = new Player(this);
                nP.setName(tmpName);
                nP.setPosition(tmpX,tmpY)
                this.playList.push(nP);
            }
            else {
                var found = playList.find(element => element.name == tmpName);
                found.setPosition(tmpX,tmpY)
            }
             ***/
        }
    }

    getMarker(){
        if(this.counter%100 !== 1)
            return
        axios('api/marker/all' ,
            {},{
            }).then(res => {
            let markers = JSON.parse(JSON.stringify(res.data))
            let markersLen = markers.length
            for(var i = 0; i <markersLen; i++) {
                var markerId = markers[i].marker_id
                var markerX = markers[i].point.x
                var markerY = markers[i].point.y
                let makerType = markers[i].type
                let isExistMarker;
                if(makerType === "Talk"){
                    isExistMarker = this.talkList.find(element => element.getCenter().x === markerX && element.getCenter().y === markerY )
                    if (isExistMarker === undefined) {
                        let newMarker = new Talk(this);
                        newMarker.setPosition(markerX, markerY)
                        newMarker.t_content.setText(markerId)
                        this.markerLayer.add(newMarker)
                        this.talkList.push(newMarker)
                    }
                    else{
                        isExistMarker.t_content.setText(markerId)
                    }
                }
                else if(makerType === "Photo"){
                    isExistMarker = this.photoList.find(element => element.getCenter().x === markerX && element.getCenter().y === markerY )
                    if (isExistMarker === undefined) {
                        let newMarker = new Photo(this);
                        newMarker.setPosition(markerX, markerY)
                        newMarker.t_content.setText(markerId)
                        this.talkList.push(newMarker)
                    }
                    else{
                        isExistMarker.t_content.setText(markerId)
                    }
                }
            }
        }).catch(error => {
            //console.log('erro', error);
        })
    }

    viewBuilding(){
        let element =  this.add.dom(1100, 780).createFromCache('buildingHtml').setScrollFactor(0);

        element.setScale(0.6)
        element.setPerspective(800);

        element.addListener('click');

        element.getChildByID('imageId').src = "/assets/images/library.png"

        element.getChildByName('description').value = "우리 도서관은 1953년 문리과대학 도서관으로 개관한 이래 현재 연면적 38,732m2 에 4,900여석의 열람석을 갖추고 190만여권의 단행본과 23,000여종의 학술지(전자저널 포함) 및 100여종의 학술DB를 제공하여 교수 및 학생들의 학문연구와 학습활동을 지원하는 학문의 요람이 되어 왔습니다.\n" +
            "1996년 도서관 학술정보시스템(CLINS)을 가동함으로써 대학도서관 전산화의 선구적 역할을 하고 있으며, 현재 모든 소장자료에 대한 서지와 학위논문·고서·교내간행물 등 원문을 인터넷상으로 이용할 수 있도록 DB구축을 완료하였고, 국내·외 학술데이터베이스 등 최신 정보를 제공하는 통합형 디지털 도서관 시스템을 구축하여 유비쿼터스 도서관으로 거듭나고 있습니다.\n" +
            "2020년 지상 2층(별관), 지하 2층으로 구성된 12,000m2 규모의 신축도서관을 기존 도서관에 증축하였습니다. 신축도서관에는 첨단 도서관으로서의 역할을 담당하는 멀티미디어실, 영상음향실, 커뮤니케이션 라운지, 북카페, VR-Room 등 이용자 중심의 시설이 자리하여 보다 이용자 친화적인 도서관으로 거듭나게 되었습니다.\n" +
            "또한 우리 도서관은 국내·외 대학도서관 및 관련 기관과의 상호협력체계를 확대하여 광범위하고 다양한 정보의 욕구를 충족시키는 학술종합센터로서의 기능을 수행하고 있으며, 더욱 쾌적한 도서관 환경을 조성하기 위하여 항상 노력하고 있습니다."

        console.log("asf!")

        element.addListener('click');

        element.on('click', function (event) {
            if (event.target.name === 'closeButton') {
                element.setVisible(false)
            }
        })
    }

    update() {
        this.counter += 1

        //console.log(this.m_player.getCenter().x , this.m_player.getCenter().y)


        if(this.m_canMove){
            this.handlePlayerMove();
        }

        if(this.m_id.text !== "noID" && this.getNicknameCount === 0){
            this.m_canMove = true;
           // //console.log(this.m_token.text)
            this.m_ui.photoButton.setVisible(true)
            this.m_ui.button.setVisible(true)
            this.m_structure.setVisible(true)
            this.getNickname(), {once: true}
            this.getNicknameCount +=1
        }
        if(this.m_player.name !== "20000000" && this.resetCharacterCount === 0){
            ////console.log(this.m_player.name)
            this.resetCharacter()
            this.resetCharacterCount += 1
            this.startPostPosition += 1
        }

        if(this.startPostPosition !==0) {
            this.timerEvent(this.resources)
            this.getMarker()
        }

        console.log(this.m_player.x, this.m_player.y)


        //this.m_ui.setPosition(this.cameras.main.centerX,this.cameras.main.centerY+181)
    }

    //////////////////////// FUNCTIONS ////////////////////////

    handlePlayerMove() {
        ////console.log(this.m_token.text);
        if (this.keyLeft.isDown) {
            this.m_player.move(Direction.Left);
            //this.m_ui.move(Direction.Left);
        } else if (this.keyRight.isDown) {
            this.m_player.move(Direction.Right);
            //this.m_ui.move(Direction.Right);
        }

        if (this.keyUp.isDown) {
            this.m_player.move(Direction.Up);
           // this.m_ui.move(Direction.Up);
        }
        else if (this.keyDown.isDown) {
            this.m_player.move(Direction.Down);
         //   this.m_ui.move(Direction.Down);
        }

        if(!this.keyLeft.isDown && !this.keyRight.isDown){
            this.m_player.setVelocityX(0);
           // this.m_ui.setVelocityX(0);
            //this.m_ui.talk.setVelocityX(0);
            //this.m_ui.photo.setVelocityX(0);
            //this.m_ui.button.setVelocityX(0);
        }

        if(!this.keyUp.isDown && !this.keyDown.isDown){
            this.m_player.setVelocityY(0);
          //  this.m_ui.setVelocityY(0);
           // this.m_ui.talk.setVelocityY(0);
           // this.m_ui.photo.setVelocityY(0);
           // this.m_ui.button.setVelocityY(0);
        }

    }

}
