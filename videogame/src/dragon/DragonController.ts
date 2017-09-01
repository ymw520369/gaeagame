enum AreaType {
		None,Dragon,Tie,Tiger
}
class DragonController extends eui.Component{
    private chip_1: eui.ToggleButton;
    private chip_2: eui.ToggleButton;
    private chip_3: eui.ToggleButton;
    private chip_4: eui.ToggleButton;
    private chip_0: eui.ToggleButton;
    private card_1:eui.Image;
    private card_1_back:eui.Image;
    private card_2:eui.Image;
    private card_2_back:eui.Image;
    private twin_png:eui.Image;
    private dwin_png:eui.Image;
    private he_png:eui.Image;

    private tieArea:BetArea;
    private tigerArea:BetArea;
    private dragonArea:BetArea;

    private _dragonValue:number = 0;
    private _tigerValue:number = 0;

    private creditText:eui.Label;
    private timeText:eui.Label;
    
    private _chipIndex:number = 0;
    private _chipValueArray:Array<number>;
    private _chipArray:Array<eui.ToggleButton>;
    private _chipValue:number = 10;
    private _chipPool:Array<eui.Image> = [];
    private _areaArray:Array<BetArea>;
  
    private _timer:egret.Timer;
    public credit:number = 10000;
    private _cardData:Object;
    private _winArea:number = 0;
    private _isBet:boolean = false;

	constructor( ) {
        super();
        this.addEventListener( eui.UIEvent.COMPLETE, this.uiCompHandler, this );
        this.skinName = "resource/game/dragon/skin/dragonSkin.exml";
    }

    private uiCompHandler():void {
        this._chipValueArray = [10,50,100,1000,10000];
        this._chipArray = [this.chip_0,this.chip_1,this.chip_2,this.chip_3,this.chip_4];
        this._areaArray = [this.dragonArea,this.tieArea,this.tigerArea];
        let chip:eui.ToggleButton;
        for(let i:number =0 ; i< 5; i++){
            chip = this._chipArray[i];
            chip.addEventListener( egret.TouchEvent.TOUCH_TAP, this.doSelectChip, this );
        }
       
        this.twin_png.visible = false;
        this.dwin_png.visible = false;
        this.he_png.visible = false;
        for (let j:number=0; j< 3; j++){
            let area:BetArea = this._areaArray[j];
            area.addEventListener(egret.TouchEvent.TOUCH_TAP,this.doTouchArea,this);
            area.type = j+1;
        }
        this._cardData = RES.getRes("config_json");

        this.test();
    }

    private test(){
        this._timer = new egret.Timer(1000,0);
        this._timer.addEventListener(egret.TimerEvent.TIMER,this.timerFunc,this);
        this._timer.addEventListener(egret.TimerEvent.TIMER_COMPLETE,this.timerComFunc,this);
        this._timer.start();
    }

    private timerFunc(event:egret.TimerEvent) {
        var time:number = (<egret.Timer>event.target).currentCount;
        //egret.log(time.toString());
        if(time == 1){
            Toast.launch("please bet !");
            this.sendCard();
        }else if(time >1 && time <21){
            this.timeText.text = (22-time).toString();
        }else if(time == 21){
            this._isBet = false;
            Toast.launch("stop bet !");
        }else if(time == 23){
            this.timeText.text = "";
            this.openCard(this.card_1,this.card_1_back);
            this.openCard(this.card_2,this.card_2_back);
        }else if(time == 24){
            this.showResult();
        }else if(time == 26){
            this.settleAccounts();
        }else if(time == 30){
            this.reset();
        }else if(time == 33){
            this._timer.reset();
            this._timer.start();
        }
    }

    private timerComFunc(event: egret.TimerEvent) {
        egret.log("timerComFunc count" + (<egret.Timer>event.target).currentCount);
    }

    private doSelectChip( evt:egret.TouchEvent ):void{
        let chip:eui.ToggleButton = evt.target;
        chip.selected = true;
        let i = this._chipArray.indexOf(chip);
        if(i != this._chipIndex){
            this._chipArray[this._chipIndex].selected = false;
            this._chipIndex = i;
        }  
        this._chipValue = this._chipValueArray[i];
    }

    private doTouchArea(evt:egret.TouchEvent):void{
        if(!this._isBet) return;
        if(this.credit < this._chipValue)return;
        let area:BetArea = evt.target; 
        let end:egret.Point = area.getChipPos();
        let chip:eui.ToggleButton = this._chipArray[this._chipIndex];
        let start:egret.Point = chip.localToGlobal(chip.width*0.5,chip.height*0.5);
        this.flyChip(start,end,area);
    }
    

    private openCard(front:eui.Image, back:eui.Image):void{
        back.scaleX = 1;
        front.scaleX = 0;
        egret.Tween.get(back).to({ scaleX: 0 }, 400, egret.Ease.backIn).call(() => {
            egret.Tween.get(front).to({ scaleX: 1 }, 400, egret.Ease.backOut);
        });
    }

     private closeCard(front:eui.Image, back:eui.Image):void{
        egret.Tween.get(front).to({ scaleX: 0 }, 400, egret.Ease.backIn).call(() => {
            egret.Tween.get(back).to({ scaleX: 1 }, 400, egret.Ease.backOut);
        });
    }


    private sendCard():void{
        this._isBet = true;
       let i:number = this.RandomNumBoth(1,52);
       //egret.log(this._cardData[i].res);
       this.card_1.source = RES.getRes(this._cardData[i].res);
       this._dragonValue = this._cardData[i].value;
       i = this.RandomNumBoth(1,52);
       //egret.log(this._cardData[i].res);
       this.card_2.source = RES.getRes(this._cardData[i].res);
       this._tigerValue = this._cardData[i].value;
    }

    private reset():void{
        this.closeCard(this.card_1,this.card_1_back);
        this.closeCard(this.card_2,this.card_2_back);
        this.twin_png.visible = false;
        this.dwin_png.visible = false;
        this.he_png.visible = false;
        for(let area of this._areaArray){
             area.reset();
         }
    }
    
    public  RandomNumBoth(Min, Max):number {
        var Range = Max - Min;
        var Rand = Math.random();
        var num = Min + Math.round(Rand * Range);     
        return num;
    }

    private showResult():void{
        if(this._dragonValue > this._tigerValue){
             this.dwin_png.visible = true;
        }else if(this._dragonValue == this._tigerValue){
            this.he_png.visible = true;
        }else{
            this.twin_png.visible = true;
        }
    }

    private flyChip(start:egret.Point,end:egret.Point,area:BetArea):void{
        let img:eui.Image = this.getChipByPool();
        this.addChild(img);
        img.x = start.x;
        img.y = start.y;
        egret.Tween.get(img).to({x:end.x,y:end.y}, 500, egret.Ease.backOut).call(() => {
            this.removeChild(img);
            this._chipPool.push(img);
            area.addBet(this._chipValue);
            this.changeCredit(-this._chipValue);
        });
    }

    private settleAccounts():void{
        if(this._dragonValue > this._tigerValue){
            this._winArea = AreaType.Dragon;   
        }else if(this._dragonValue == this._tigerValue){
           this._winArea = AreaType.Tie;  
        }else{
            this._winArea = AreaType.Tiger;  
        }
        let sum:number = 0
        for(let area of this._areaArray){
             area.settle(this._winArea);
             sum += area.winCoin;
         }
         this.changeCredit(sum);
    }

    private getChipByPool():eui.Image {
        let chip:eui.Image;
        if(this._chipPool.length > 0){
            chip = this._chipPool.pop();
        }else{
            chip = new eui.Image();
            chip.source = RES.getRes("coin s_png");
        }
        return chip;
    }

    private changeCredit(value:number):void{
        this.credit += value;
        this.creditText.text = this.credit.toString();
    }
    
}