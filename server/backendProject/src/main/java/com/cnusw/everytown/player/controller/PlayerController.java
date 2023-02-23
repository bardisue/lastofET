package com.cnusw.everytown.player.controller;

import com.cnusw.everytown.player.dto.NicknameDto;
import com.cnusw.everytown.player.dto.PlayerPointDto;
import com.cnusw.everytown.player.dto.PointDto;
import com.cnusw.everytown.player.service.PlayerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PlayerController {

    @Autowired
    PlayerService playerService;

    @PostMapping("/player/create_userName")
    @ResponseBody
    public ResponseEntity createUserName(@RequestBody PointDto dto){
        String tmpName = playerService.makeRandomName();
        PlayerPointDto tmpDto = new PlayerPointDto(dto, tmpName);
        playerService.createPoint(tmpDto);
        return ResponseEntity.status(HttpStatus.OK).body(new NicknameDto(tmpName));
    }

    @PostMapping("/player/create_user")
    @ResponseBody
    public ResponseEntity createUser(@RequestBody PlayerPointDto dto){
        playerService.createPoint(dto);
        return ResponseEntity.status(HttpStatus.OK).body(new NicknameDto(dto.getNickname()));
    }

    @GetMapping("/player")
    @ResponseBody
    public ResponseEntity<List<PlayerPointDto>> getAllPoints(){
        return ResponseEntity.ok(playerService.getAllPoint());
    }

    @PostMapping("/player/save")
    @ResponseBody
    public ResponseEntity saveUserPoint(@RequestBody PlayerPointDto dto){
        playerService.createPoint(dto);
        return ResponseEntity.ok().build();
    }

    /***
    @PostMapping("/player/save")
    @ResponseBody
    public ResponseEntity updatePlayerName(@RequestBody PlayerPointDto dto){
        playerService.updateName(dto);
        return ResponseEntity.ok().build();
    }
    ***/
}
