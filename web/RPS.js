$(document).ready(function() {
    eventManager();
});
const moves = {
    ROCK: "ROCK",
    PAPER: "PAPER",
    SCISSORS: "SCISSORS"
}
let bestof;
let current = 0;
let player;
let myMove;
let player1;
let score1 = 0;
let player2;
let score2 = 0;
let p1Move;
let p2Move;

function eventManager() {
    $("#rock").click(moves.ROCK, select);
    $("#paper").click(moves.PAPER, select);
    $("#sicssors").click(moves.SCISSORS, select);
    $("#firstPage").hide();
    $("#loginf").hide();
    connect();
}
function unselectMoves() {
    $("#rock").removeClass("selector");
    $("#paper").removeClass("selector");
    $("#sicssors").removeClass("selector");
}
function select(move) {

    myMove = move.data;

    unselectMoves();

    switch (move.data) {
        case moves.ROCK:
            $("#rock").addClass("selector");
            break;
        case moves.PAPER:
            $("#paper").addClass("selector");
            break;
        case moves.SCISSORS:
            $("#sicssors").addClass("selector");
            break;
    }
}

var stompClient = null;

function setConnected(connected) {
    if(connected){
        $("#enclosed").hide();
        $("#firstPage").show();
        $("#setTurns").click(temp);
    }
}
function temp() {
    startGame($("#numOfTurns").val());
}
var resetTimeout;
function connect() {
    var socket = new SockJS('http://127.0.0.1:8080/rpc');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);

        stompClient.subscribe("/topic/getWinner", function (resp) {
            var winnerData = JSON.parse(resp.body);
            $("#status").text(winnerData.toString());
            var data = winnerData.toString().split(",");
            var p = data[0].split("-");
            if(p[1] == "WIN"){
                score1++;
            }else{
                score2++;
            }
            if(bestof > 0){
                bestof--;
                displayPlayerNames();
                clearTimeout(resetTimeout);
                resetTimeout = setTimeout(reset, 2000);
            }else {
                if(score1 > score2){
                    $("#status").text(player1 + " WINS!");
                }else if(score2 > score1){
                    $("#status").text(player2 + " WINS!");
                }else {
                    $("#status").text("DRAW!");
                }
            }
        });

        stompClient.subscribe("/topic/gameDetails", function (resp) {
            var obj = JSON.parse(resp.body);
            player1 = obj.playerOne.username;
            player2 = obj.playerTwo.username;
            bestof = obj.bestof;
            if(obj.playerOne !== null && obj.playerTwo !== null){
                $("#loginf").hide();
                $("#enclosed").show();
                displayPlayerNames();
                timer(10);
            }
            p1Move = obj.playerOne.move;
            p2Move = obj.playerTwo.move;
            displayMove();
            //outPutResponse(JSON.parse(resp.body).content);
        });

        stompClient.subscribe("/topic/gameDetails", function (resp) {
            outPutResponse(JSON.parse(resp.body).content);
        });
    });
}
function reset() {
    $("#status").text("");
    unselectMoves();
    timer(10);
}
function registerPlayer(username){
    stompClient.send("/game/registerPlayer", {}, username);
    player = username;
}
function displayMove() {
    switch (p1Move) {
        case moves.ROCK:
            $("#plR").attr("src","RockRev.png");
            break;
        case moves.PAPER:
            $("#plR").attr("src","PaperRev.png");
            break;
        case moves.SCISSORS:
            $("#plR").attr("src","SicssorsRev.png");
            break;
    }
    switch (p2Move) {
        case moves.ROCK:
            $("#pl2").attr("src","Rock.png");
            break;
        case moves.PAPER:
            $("#pl2").attr("src","Paper.png");
            break;
        case moves.SCISSORS:
            $("#pl2").attr("src","Sicssors.png");
            break;
    }
    setTimeout(resetIcons, 2000)
}
function resetIcons() {
    $("#plR").attr("src","RockRev.png");
    $("#pl2").attr("src","Rock.png");
}
function startGame(bestOf){
    if(bestOf !== '') {
        stompClient.send("/game/start", {}, bestOf);
        $("#firstPage").hide();
        $("#loginf").show();
        $("#setPlayer").click(register);
    }
}
function makeMove(username, move){
    stompClient.send("/game/makeMove", {}, JSON.stringify({'username': username, "move": move}));
}

function getWinner(){
    stompClient.send("/game/getWinner", {}, {});
}

function getGame(){
    stompClient.send("/game/getGame", {}, {});
}

function register(){
    var name = $('#pname').val();
    if(name !== '' || player != null){
        registerPlayer(name);
        getGame();
    }
}
function displayPlayerNames() {
    $("#p1name").text(player1 + " : " + score1);
    $("#p2name").text(player2 + " : " + score2);
    $("#boo").text("Best of " + bestof);
}
function outPutResponse(body){
    console.log(body);
}

function animate() {
    $("#main").addClass("anim");
    setTimeout(stopAnimation, 900)
}
function stopAnimation() {
    $("#main").removeClass("anim");
    getResult();
}
var myTimer;
function timer(time) {
    $("#tim").text(time);
    if(time <= 0){
        animate();
        makeMove(player, myMove);
        getGame();
        getWinner();
    }else {
        clearTimeout(myTimer);
        myTimer = setTimeout(timer, 1000, time-1);
    }
}

function getResult() {

}