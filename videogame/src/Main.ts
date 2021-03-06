class Main extends eui.UILayer {
    private loadingView: LoadingUI;
    private _trueLoadingUI:TrueLoadingUI;
    private idLoading:string;
    private isResourceLoadEnd: boolean = false;
    private isThemeLoadEnd: boolean = false;
    private game:egret.DisplayObject = null;
    private wsurl:string = "";

    protected createChildren(): void {
        super.createChildren();
       //this.stage.scaleMode = egret.StageScaleMode.FIXED_WIDTH;
        egret.lifecycle.addLifecycleListener((context) => {
            //custom lifecycle plugin;
        })

        egret.lifecycle.onPause = () => {
           egret.ticker.pause();
        }

        egret.lifecycle.onResume = () => {
           egret.ticker.resume();
           if(App.GlobalData.isLogin)
                App.GlobalData.isResume = true;
           
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
        RES.loadGroup("preload");
    }
    
    private onThemeLoadComplete(): void {
        this.isThemeLoadEnd = true;
        this.createScene();
    }
   
    private onResourceLoadComplete(event: RES.ResourceEvent): void {
         switch (event.groupName ) {
            case "preload":
                if( this.loadingView.parent ){
                    this.loadingView.parent.removeChild( this.loadingView );
                }
                Toast.init(this, RES.getRes("toast-bg_png")); 
                this._trueLoadingUI = new TrueLoadingUI();
                this.loadPage("proto");
                break;
            case "proto":
                this.loadPage("catdog");
                break;
            case "catdog":
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
            this.loginCS();
            App.SoundManager.setBgOn(true);
            App.SoundManager.setEffectOn(true);
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
            case "preload":
                this.loadingView.setProgress(event.itemsLoaded, event.itemsTotal);
                break;
            default :
                this._trueLoadingUI.setProgress(event.itemsLoaded, event.itemsTotal);
                break;
        }
    }
   

    private loginCS():void{
        
        var config = RES.getRes("config_json");
         let obj:Object = new Object();
        //  if(config.test){
        //     obj["sessionId"] = "token";
        //     obj["userId"] = "1001";
        //     obj["roomId"] = "1234";
        //  }else{
             obj = window["deviceObj"];
         //}
         
        var params = "token=" +obj["sessionId"]+ "&uuid="+obj["userId"] +"&roomId="+obj["roomId"];   
        //alert(params);
        var url:string = config.httpURL;
        var request:egret.HttpRequest =  new egret.HttpRequest();
        request.addEventListener(egret.Event.COMPLETE, this.onPostComplete, this);
        request.responseType = egret.HttpResponseType.TEXT;
        request.open(url,egret.HttpMethod.POST);
        request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        request.send(params);
    }
    private token:string = "";
    private playerid:number = 1;
    private sign:string = "";
    private onPostComplete(event:egret.Event):void
    {
        let request:egret.HttpRequest = <egret.HttpRequest>event.currentTarget;
        request.removeEventListener(egret.Event.COMPLETE, this.onPostComplete, this);
        //console.log(request.response);
        var param:Object = JSON.parse(request.response);
        
        var wsurl:string = param["logicUrl"];
        this.wsurl = wsurl;
        var credential  = param["credential"];
        this.token = credential.certifyToken;
        this.playerid = credential.playerId;
        
        if(credential.sign)
            this.sign = credential.sign;
        App.MessageCenter.addListener(LobbyConst.SOCKET_OPEN,this.doSocketOpen,this);
        SocketManager.connectServer(wsurl);
    }

    public doSocketOpen(param:Object):void{
         App.MessageCenter.removeListener(LobbyConst.SOCKET_OPEN,this.doSocketOpen,this);
        this.doCredentialCS();   
    }

    private doCredentialCS(){
         var Credential_class = App.Proto.build("Credential");
         var cred = new Credential_class();
         cred.certifyToken = this.token;
         cred.playerId = this.playerid;
         cred.sign = this.sign;
         var bytes = cred.toArrayBuffer();
         SocketManager.sendMessage(1000,1,bytes);

         App.MessageCenter.register(1000,2,this.doCredentialSC,this);
         App.MessageCenter.register(900,901,this.doTipsSC,this);
    }

     private doCredentialSC(param:any){
         var MyClass = App.Proto.build("LoginResult");
        var obj = MyClass.decode(param);
        App.GlobalData.role = obj.player.role;
        
       App.MessageCenter.register(1200,1202,this.doEnterRoomSC,this);
       this.doJoinRoomCS();
     }

    protected startCreateScene(): void {
        this.pageLoadedHandler( "catdog" );  
        if(this.game != null)
            this.removeChild(this.game);
        let dragon:DragonController = new DragonController();   
        this.addChild(dragon);
        this.game = dragon;
    }

   private doCreateRoomCS():void{
        var MyClass = App.Proto.build("CreateGame");
        var createGame = new MyClass();
        createGame.gameType = 0;
        var bytes = createGame.toArrayBuffer();
        SocketManager.sendMessage(1100,1101,bytes);
    }

     private doJoinRoomCS():void{
         //console.log("doJoinRoomCS");
        var MyClass = App.Proto.build("JoinRoom");
        var createGame = new MyClass();
        createGame.roomId = 1;
        var bytes = createGame.toArrayBuffer();
        SocketManager.sendMessage(1100,1105,bytes);
    }
    
    private doEnterRoomSC(param):void{
       // console.log("doEnterRoomSC");
        var MyClass = App.Proto.build("LHDGameInfo");
        var obj = MyClass.decode(param);
        App.GlobalData.gameData = obj;
        this.startCreateScene();
        App.GlobalData.isLogin = true;
    }

     private doGetStatusCS():void{
         if( App.GlobalData.isLogin){
             var MyClass = App.Proto.build("StatusMessage");
            var status = new MyClass();
             status.flag = true;
             var bytes = status.toArrayBuffer();
             SocketManager.sendMessage(1200,1209,bytes);
         }
    }

    private doTipsSC(param:any){
         var MyClass = App.Proto.build("GameTips");
         var obj = MyClass.decode(param);
         Toast.launch(obj.resultDes);
         //console.log(obj.resultDes);
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
