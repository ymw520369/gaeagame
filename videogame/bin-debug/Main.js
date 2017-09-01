var __reflect = (this && this.__reflect) || function (p, c, t) {
    p.__class__ = c, t ? t.push(c) : t = [c], p.__types__ = p.__types__ ? t.concat(p.__types__) : t;
};
var __extends = (this && this.__extends) || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
};
var Main = (function (_super) {
    __extends(Main, _super);
    function Main() {
        var _this = _super !== null && _super.apply(this, arguments) || this;
        _this.isResourceLoadEnd = false;
        _this.isThemeLoadEnd = false;
        return _this;
    }
    Main.prototype.createChildren = function () {
        _super.prototype.createChildren.call(this);
        egret.lifecycle.addLifecycleListener(function (context) {
            // custom lifecycle plugin
        });
        egret.lifecycle.onPause = function () {
            egret.ticker.pause();
        };
        egret.lifecycle.onResume = function () {
            egret.ticker.resume();
        };
        var assetAdapter = new AssetAdapter();
        egret.registerImplementation("eui.IAssetAdapter", assetAdapter);
        egret.registerImplementation("eui.IThemeAdapter", new ThemeAdapter());
        this.loadingView = new LoadingUI();
        this.stage.addChild(this.loadingView);
        RES.addEventListener(RES.ResourceEvent.CONFIG_COMPLETE, this.onConfigComplete, this);
        RES.loadConfig("resource/default.res.json", "resource/");
    };
    Main.prototype.onConfigComplete = function (event) {
        RES.removeEventListener(RES.ResourceEvent.CONFIG_COMPLETE, this.onConfigComplete, this);
        var theme = new eui.Theme("resource/default.thm.json", this.stage);
        theme.addEventListener(eui.UIEvent.COMPLETE, this.onThemeLoadComplete, this);
        RES.addEventListener(RES.ResourceEvent.GROUP_COMPLETE, this.onResourceLoadComplete, this);
        RES.addEventListener(RES.ResourceEvent.GROUP_LOAD_ERROR, this.onResourceLoadError, this);
        RES.addEventListener(RES.ResourceEvent.GROUP_PROGRESS, this.onResourceProgress, this);
        RES.addEventListener(RES.ResourceEvent.ITEM_LOAD_ERROR, this.onItemLoadError, this);
        RES.loadGroup("loading");
    };
    Main.prototype.onThemeLoadComplete = function () {
        this.isThemeLoadEnd = true;
        this.createScene();
    };
    Main.prototype.onResourceLoadComplete = function (event) {
        switch (event.groupName) {
            case "loading":
                if (this.loadingView.parent) {
                    this.loadingView.parent.removeChild(this.loadingView);
                }
                Toast.init(this, RES.getRes("toast-bg_png"));
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
            default:
                this.pageLoadedHandler(event.groupName);
                break;
        }
    };
    Main.prototype.loadPage = function (pageName) {
        this.addChild(this._trueLoadingUI);
        this.idLoading = pageName;
        RES.loadGroup(pageName);
    };
    Main.prototype.createScene = function () {
        if (this.isThemeLoadEnd && this.isResourceLoadEnd) {
            this.startCreateScene();
        }
    };
    Main.prototype.onItemLoadError = function (event) {
        console.warn("Url:" + event.resItem.url + " has failed to load");
    };
    Main.prototype.onResourceLoadError = function (event) {
        console.warn("Group:" + event.groupName + " has failed to load");
        this.onResourceLoadComplete(event);
    };
    Main.prototype.onResourceProgress = function (event) {
        switch (event.groupName) {
            case "loading":
                this.loadingView.setProgress(event.itemsLoaded, event.itemsTotal);
                break;
            default:
                this._trueLoadingUI.setProgress(event.itemsLoaded, event.itemsTotal);
                break;
        }
    };
    Main.prototype.startCreateScene = function () {
        this.pageLoadedHandler("game");
        var dragon = new DragonController();
        this.addChild(dragon);
    };
    Main.prototype.createBitmapByName = function (name) {
        var result = new egret.Bitmap();
        var texture = RES.getRes(name);
        result.texture = texture;
        return result;
    };
    Main.prototype.onButtonClick = function (e) {
    };
    Main.prototype.pageLoadedHandler = function (name) {
        if (this._trueLoadingUI.parent) {
            this._trueLoadingUI.parent.removeChild(this._trueLoadingUI);
        }
    };
    Main.prototype.clearRESEvents = function () {
        RES.removeEventListener(RES.ResourceEvent.GROUP_COMPLETE, this.onResourceLoadComplete, this);
        RES.removeEventListener(RES.ResourceEvent.GROUP_LOAD_ERROR, this.onResourceLoadError, this);
        RES.removeEventListener(RES.ResourceEvent.GROUP_PROGRESS, this.onResourceProgress, this);
        RES.removeEventListener(RES.ResourceEvent.ITEM_LOAD_ERROR, this.onItemLoadError, this);
    };
    return Main;
}(eui.UILayer));
__reflect(Main.prototype, "Main");
//# sourceMappingURL=Main.js.map