class Main extends eui.UILayer {
    private loadingView: LoadingUI;
    private _trueLoadingUI:TrueLoadingUI;
    private idLoading:string;
    private isResourceLoadEnd: boolean = false;
    private isThemeLoadEnd: boolean = false;

    protected createChildren(): void {
        super.createChildren();

        egret.lifecycle.addLifecycleListener((context) => {
            // custom lifecycle plugin
        })

        egret.lifecycle.onPause = () => {
            egret.ticker.pause();
        }

        egret.lifecycle.onResume = () => {
            egret.ticker.resume();
        }

        let assetAdapter = new AssetAdapter();
        egret.registerImplementation("eui.IAssetAdapter", assetAdapter);
        egret.registerImplementation("eui.IThemeAdapter", new ThemeAdapter());
        this.loadingView = new LoadingUI();
        this.stage.addChild(this.loadingView);
        RES.addEventListener(RES.ResourceEvent.CONFIG_COMPLETE, this.onConfigComplete, this);
        RES.loadConfig("resource/default.res.json", "resource/");
    }
 
    private onConfigComplete(event: RES.ResourceEvent): void {
        RES.removeEventListener(RES.ResourceEvent.CONFIG_COMPLETE, this.onConfigComplete, this);
        let theme = new eui.Theme("resource/default.thm.json", this.stage);
        theme.addEventListener(eui.UIEvent.COMPLETE, this.onThemeLoadComplete, this);

        RES.addEventListener(RES.ResourceEvent.GROUP_COMPLETE, this.onResourceLoadComplete, this);
        RES.addEventListener(RES.ResourceEvent.GROUP_LOAD_ERROR, this.onResourceLoadError, this);
        RES.addEventListener(RES.ResourceEvent.GROUP_PROGRESS, this.onResourceProgress, this);
        RES.addEventListener(RES.ResourceEvent.ITEM_LOAD_ERROR, this.onItemLoadError, this);
        RES.loadGroup("loading");
    }
    
    private onThemeLoadComplete(): void {
        this.isThemeLoadEnd = true;
        this.createScene();
    }
   
    private onResourceLoadComplete(event: RES.ResourceEvent): void {
         switch (event.groupName ) {
            case "loading":
                if( this.loadingView.parent ){
                    this.loadingView.parent.removeChild( this.loadingView );
                }
                Toast.init(this, RES.getRes("toast-bg_png" ) ); 
                //this._loadingBg = new egret.Bitmap(RES.getRes("loading_bg"));
                //this.addChild( this._loadingBg );
                this._trueLoadingUI = new TrueLoadingUI();
                this.loadPage("game");
                break;
            case "game":
                /// clearRESEvents
                this.isResourceLoadEnd = true;
                this.createScene();
                break;
            default :
                this.pageLoadedHandler( event.groupName );
                break;
        } 
    }

     private loadPage( pageName:string ):void{
        this.addChild(this._trueLoadingUI );
        this.idLoading = pageName; 
        RES.loadGroup( pageName );
    }

    private createScene() {
        if (this.isThemeLoadEnd && this.isResourceLoadEnd) {
            this.startCreateScene();
        }
    }

    private onItemLoadError(event: RES.ResourceEvent): void {
        console.warn("Url:" + event.resItem.url + " has failed to load");
    }

    private onResourceLoadError(event: RES.ResourceEvent): void {
        console.warn("Group:" + event.groupName + " has failed to load");
        this.onResourceLoadComplete(event);
    }

    private onResourceProgress(event: RES.ResourceEvent): void {
         switch (event.groupName) {
            case "loading":
                this.loadingView.setProgress(event.itemsLoaded, event.itemsTotal);
                break;
            default :
                this._trueLoadingUI.setProgress(event.itemsLoaded, event.itemsTotal);
                break;
        }
    }
   

    protected startCreateScene(): void {
        this.pageLoadedHandler( "game" );
        let dragon:DragonController = new DragonController();
        this.addChild(dragon);
    }
 
    private createBitmapByName(name: string): egret.Bitmap {
        let result = new egret.Bitmap();
        let texture: egret.Texture = RES.getRes(name);
        result.texture = texture;
        return result;
    }
 
    private onButtonClick(e: egret.TouchEvent) {
    }

    private pageLoadedHandler( name:string ):void{
        if( this._trueLoadingUI.parent ){
            this._trueLoadingUI.parent.removeChild( this._trueLoadingUI );
        }
    }

    private clearRESEvents():void{
        RES.removeEventListener(RES.ResourceEvent.GROUP_COMPLETE, this.onResourceLoadComplete, this);
        RES.removeEventListener(RES.ResourceEvent.GROUP_LOAD_ERROR, this.onResourceLoadError, this);
        RES.removeEventListener(RES.ResourceEvent.GROUP_PROGRESS, this.onResourceProgress, this);
        RES.removeEventListener(RES.ResourceEvent.ITEM_LOAD_ERROR, this.onItemLoadError, this);
    }
}
