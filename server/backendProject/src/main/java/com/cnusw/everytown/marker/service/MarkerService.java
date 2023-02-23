package com.cnusw.everytown.marker.service;

import com.cnusw.everytown.marker.dto.*;

import com.cnusw.everytown.marker.dto.MarkerDetailResponse.*;
import com.cnusw.everytown.marker.converter.Converter;
import com.cnusw.everytown.marker.entity.LossMarker;
import com.cnusw.everytown.marker.entity.Marker;
import com.cnusw.everytown.marker.entity.PhotoMarker;
import com.cnusw.everytown.marker.entity.TalkMarker;
import com.cnusw.everytown.marker.repository.*;
import com.cnusw.everytown.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;



@Service
public class MarkerService {

    // 생성자 주입 (필요한 repository)
    private final MarkerRepository markerRepository;
    private final LossMarkerRepository lossMarkerRepository;
    private final TalkMarkerRepository talkMarkerRepository;
    private final PhotoMarkerRepository photoMarkerRepository;
    private final UserRepository userRepository;

    @Autowired
    public MarkerService(MarkerRepository markerRepository,
                         LossMarkerRepository lossMarkerRepository,
                         TalkMarkerRepository talkMarkerRepository,
                         PhotoMarkerRepository photoMarkerRepository,
                         UserRepository userRepository) {
        this.markerRepository = markerRepository;
        this.lossMarkerRepository = lossMarkerRepository;
        this.talkMarkerRepository = talkMarkerRepository;
        this.photoMarkerRepository = photoMarkerRepository;
        this.userRepository = userRepository;
    }

    // ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 1. 마커 생성 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ

    // Talk Marker를 생성한다.
    public int makeTalkMarker(TalkMarkerCreatedRequest talkDto) throws MarkerPointExistsException {
        PointDto point = talkDto.getPoint();
        checkDuplicatePoint(point.getX(), point.getY());
        TalkMarker talkMarker = TalkMarker.builder()
                .user(userRepository.findById(talkDto.getUser_id()).get())
                .x(talkDto.getPoint().getX())
                .y(talkDto.getPoint().getY())
                .created_datetime(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .title(talkDto.getTitle())
                .contents(talkDto.getContents())
                .marker_type("Talk")
                .build();
        talkMarkerRepository.save(talkMarker);
        return talkMarker.getId();
    }

    // Photo Marker를 생성한다.
    public int makePhotoMarker(PhotoMarkerCreatedRequest photoDto) throws MarkerPointExistsException {
        PointDto point = photoDto.getPoint();
        checkDuplicatePoint(point.getX(), point.getY());
        PhotoMarker photoMarker = PhotoMarker.builder()
                .user(userRepository.findById(photoDto.getUser_id()).get())
                .x(photoDto.getPoint().getX())
                .y(photoDto.getPoint().getY())
                .created_datetime(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .title(photoDto.getTitle())
                .contents(photoDto.getContents())
                .url(photoDto.getUrl())
                .marker_type("Photo")
                .build();
        photoMarkerRepository.save(photoMarker);
        return photoMarker.getId();
    }

    // Loss Marker를 생성한다.
    public int makeLossMarker(LossMarkerCreatedRequest lossDto) throws MarkerPointExistsException {
        PointDto point = lossDto.getPoint();
        checkDuplicatePoint(point.getX(), point.getY());
        LossMarker lossMarker = LossMarker.builder()
                .user(userRepository.findById(lossDto.getUser_id()).get())
                .x(lossDto.getPoint().getX())
                .y(lossDto.getPoint().getY())
                .created_datetime(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .contents(lossDto.getContents())
                .marker_type("Loss")
                .build();
        lossMarkerRepository.save(lossMarker);
        return lossMarker.getId();
    }

    // 생성하려는 마커 좌표(x, y)가 이미 존재하는지 검증한다.
    private void checkDuplicatePoint(int x, int y) throws MarkerPointExistsException {
        if (markerRepository.existsByPoint(x, y).isPresent()){
            throw new MarkerPointExistsException("Marker point is duplicated.");
        }
    }


    // ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 2. 마커 조회 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ

    public List<MarkerSimpleReadAllResponse> readAllMarkers() {
        List<Marker> markers = markerRepository.findAll();
        return markers.stream().map(Converter::toMarkerSimpleReadAllResponse).collect(Collectors.toList());
    }

    public List<MarkerSimpleResponse> readAllTalkMarkers() {
        List<TalkMarker> markers = talkMarkerRepository.findAll();
        return markers.stream().map(Converter::toMarkerSimpleResponse).collect(Collectors.toList());
    }

    public List<MarkerSimpleResponse> readAllLossMarkers() {
        List<LossMarker> markers = lossMarkerRepository.findAll();
        return markers.stream().map(Converter::toMarkerSimpleResponse).collect(Collectors.toList());
    }

    public List<MarkerSimpleResponse> readAllPhotoMarkers() {
        List<PhotoMarker> markers = photoMarkerRepository.findAll();
        return markers.stream().map(Converter::toMarkerSimpleResponse).collect(Collectors.toList());
    }

    public LossMarkerResponse getLossMarkerById(int id) {
        LossMarker lossMarker = lossMarkerRepository.findById(id).get();
        return new LossMarkerResponse(lossMarker.getContents(), lossMarker.getCreated_datetime(), lossMarker.getUser().getNickname());
    }

    public TalkMarkerResponse getTalkMarkerById(int id) {
        TalkMarker talkMarker = talkMarkerRepository.findById(id).get();
        return new TalkMarkerResponse(talkMarker.getTitle(), talkMarker.getContents(), talkMarker.getCreated_datetime(), talkMarker.getUser().getNickname());
    }

    public PhotoMarkerResponse getPhotoMarkerById(int id) {
        PhotoMarker photoMarker = photoMarkerRepository.findById(id).get();
        return new PhotoMarkerResponse(photoMarker.getUrl(), photoMarker.getTitle(), photoMarker.getContents(), photoMarker.getCreated_datetime(), photoMarker.getUser().getNickname());
    }
}



