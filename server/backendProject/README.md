# Everytown
동계 1팀

## Git

### 1. 가장 먼저 프로젝트를 로컬로 가져오기
```git clone https://github.com/CNU-Software-Academy/Everytown.git ``` (HTTPS)<br />
or<br />
```git clone git@github.com:CNU-Software-Academy/Everytown.git``` (SSH)

### 작업 후 팀 repo에 올릴 때 순서
1. ```git remote -v ``` // 깃헙이랑 잘 연결되어있는지 확인, 안되어있을땐 폴더 위치 잘 확인해보기
2. ```git pull origin <브랜치명>``` // 다른 사람이 올린거 날아가지 않게 pull 받아서 파일 최신화
3. ```git status``` // 로 변경사항 확인 modified : 빨간색글씨 옆에 파일명이 뜸.
4. ```git add (파일명)``` // 특정 파일 추가(git status를 입력햇을 때 뜨는 경로둘 복사해서 하나하나 넣는 거 추천)<br />
   or<br />
   ```git add .``` // 디렉토리 내 모든 파일 추가 <br />
   add가 잘되었는지 보려면 git status 다시 입력해보면 add된 파일이 초록색으로 바뀜
5. ```git commit -m '메시지'``` // 커밋

6. ```git push origin <브랜치명>``` // main 브랜치에 올릴경우 git push origin main

공동파일 수정시 팀원들에게 말하고 푸쉬하기(ex) .properties, config 파일 등 ) <br />
작업 내용 push 전후에 말하기!

### 브랜치 생성
```git checkout -b <브랜치명>``` // 브랜치 생성 후 해당 브랜치로 이동 <br />
그리고 똑같이 위의 순서 따라해서 팀 레포에 올리면 팀 레포에 자동으로 브랜치 생성<br />


```git checkout <브랜치명>``` // 해당 브랜치로 이동