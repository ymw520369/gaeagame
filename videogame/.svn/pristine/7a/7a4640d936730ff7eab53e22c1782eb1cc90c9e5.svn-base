
class BetArea extends eui.Component implements  eui.UIComponent {
	public betText:eui.Label;
	public roleNameImg:eui.Image;
	public roleImg:eui.Image;
	private winText:eui.Label;
	private addsText:eui.Label;

	public isWin:boolean = false;
	private _adds : number = 1;

	private _type : number = 0;
	public get type() : number {
		return this._type;
	}
	public set type(v : number) {
		this._type = v;
		if(v == AreaType.Dragon )this._adds = 2;
		else if(v == AreaType.Tiger )this._adds = 2;
		else if(v == AreaType.Tie)this._adds = 16;
	}
	
	private _bet:number =0;
	public get bet(){
		return this._bet;
	}
	public set bet (value:number){
		this._bet = value;
	}

	private _winCoin : number = 0;
	public get winCoin() : number {
		return this._winCoin;
	}
	public set winCoin(v : number) {
		this._winCoin = v;
	}
	
	public reset():void{
		this.winText.visible = false;
		this.betText.visible = false;
		
		this._bet = 0;
		this._winCoin = 0;
	}

	public addBet(value:number):void{
		this._bet += value ;
		this.betText.visible = true;
		this.betText.text = this._bet.toString();
		
	}
	
	
	public constructor() {
		super();
	}

	protected partAdded(partName:string,instance:any):void
	{
		super.partAdded(partName,instance);
		this.addEventListener( eui.UIEvent.COMPLETE, this.uiCompHandler, this );
	}


	protected childrenCreated():void
	{
		super.childrenCreated();
	}

	private uiCompHandler():void {
		let resName:string = "";
		let roleName:string = "";
		if(this.type == AreaType.Dragon){
			resName = "pixelcat_png";
			roleName ="cat_png";
			this.addsText.text = "1:2";
		}else if(this.type  == AreaType.Tie){
			resName = "pixeltie_png";
			roleName ="tie_png";
			this.addsText.text = "1:8";
		}else if(this.type  == AreaType.Tiger){
			resName = "pixeldog_png";
			roleName ="dog_png";
			this.addsText.text = "1:2";
		}
		this.roleImg.source = RES.getRes(resName);
		this.roleNameImg.source = RES.getRes(roleName);
		this.reset();
		this.touchChildren = false;
	}

	public settle(winType:number):void{
		if(this._bet ==0)return;
		this.winText.visible = true;
		this.isWin = (winType == this.type);
		if(this.isWin){
			this._winCoin = this._bet * this._adds;
			this.winText.text = "+" + this._winCoin;
		}else{
			this._winCoin = 0;
			this.winText.text = "-" + this._bet;
		}
	}

	public getChipPos():egret.Point{
		return this.localToGlobal(this.width*0.5, this.height*0.5);
	}
}