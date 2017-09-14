class SettlePop extends eui.Component implements  eui.UIComponent {
	public playerText:eui.Label;
	public bankerText:eui.Label;
	public lose_png:eui.Image;
	public win_png:eui.Image;

	public constructor() {
		super();
	}

	protected partAdded(partName:string,instance:any):void
	{
		super.partAdded(partName,instance);
	}


	protected childrenCreated():void
	{
		super.childrenCreated();
	}
	public show(pWin:number,bWin:number):void{
		var isWin = pWin>0;
		this.lose_png.visible = !isWin;
		this.win_png.visible = isWin;
		if(pWin >= 0 )
			this.playerText.text = "+" + pWin;
		else this.playerText.text = pWin.toString();
		if(bWin >= 0 )
			this.bankerText.text = "+" + bWin;
		else this.bankerText.text = bWin.toString();
		this.visible = true;
		this.x = -600;
		egret.Tween.get(this).to({x:116 }, 600, egret.Ease.backOut);
       
	}

	public hide():void{
		if(!this.visible)return;
		egret.Tween.get(this).to({x:800 }, 600, egret.Ease.backOut).call(() => {
            this.visible = false;
        });
		
	}
}