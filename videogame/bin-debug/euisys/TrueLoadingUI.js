/**
 * Created by egret on 2016/1/20.
 */
var __reflect = (this && this.__reflect) || function (p, c, t) {
    p.__class__ = c, t ? t.push(c) : t = [c], p.__types__ = p.__types__ ? t.concat(p.__types__) : t;
};
var __extends = (this && this.__extends) || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
};
var TrueLoadingUI = (function (_super) {
    __extends(TrueLoadingUI, _super);
    function TrueLoadingUI() {
        var _this = _super.call(this) || this;
        _this.addEventListener(egret.Event.REMOVED_FROM_STAGE, function () {
            if (_this.hasEventListener(egret.Event.ENTER_FRAME)) {
                _this.removeEventListener(egret.Event.ENTER_FRAME, _this.runLoading, _this);
            }
        }, _this);
        _this.addEventListener(egret.Event.ADDED_TO_STAGE, function () {
            _this.setProgress(0, 1);
            _this.addEventListener(egret.Event.ENTER_FRAME, _this.runLoading, _this);
        }, _this);
        return _this;
    }
    TrueLoadingUI.prototype.createChildren = function () {
        _super.prototype.createChildren.call(this);
        this._loadingRun = new egret.Bitmap(RES.getRes("loading2_png"));
        this.addChild(this._loadingRun);
        this._loadingRun.anchorOffsetX = this._loadingRun.width * .5;
        this._loadingRun.anchorOffsetY = this._loadingRun.height * .5;
        this._loadingRun.x = this.stage.stageWidth * .5;
        this._loadingRun.y = this.stage.stageHeight * .5;
        this._txProgress = new egret.TextField;
        this._txProgress.textAlign = egret.HorizontalAlign.CENTER;
        this._txProgress.verticalAlign = egret.VerticalAlign.MIDDLE;
        this._txProgress.x = this.stage.stageWidth * .5 - 100;
        this._txProgress.width = 200;
        this._txProgress.y = this.stage.stageHeight * .5 - 100;
        this._txProgress.height = 200;
        this._txProgress.size = 16;
        this._txProgress.stroke = 1;
        this._txProgress.strokeColor = 0;
        this.addChild(this._txProgress);
    };
    TrueLoadingUI.prototype.runLoading = function (evt) {
        this._loadingRun.rotation += 3;
    };
    TrueLoadingUI.prototype.setProgress = function (itemsLoaded, itemsTotal) {
        if (this._txProgress) {
            this._txProgress.text = Math.round(itemsLoaded / itemsTotal * 100) + "%";
        }
    };
    return TrueLoadingUI;
}(eui.Group));
__reflect(TrueLoadingUI.prototype, "TrueLoadingUI");
//# sourceMappingURL=TrueLoadingUI.js.map