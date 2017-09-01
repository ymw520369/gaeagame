var __reflect = (this && this.__reflect) || function (p, c, t) {
    p.__class__ = c, t ? t.push(c) : t = [c], p.__types__ = p.__types__ ? t.concat(p.__types__) : t;
};
var __extends = (this && this.__extends) || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
};
var BetArea = (function (_super) {
    __extends(BetArea, _super);
    function BetArea() {
        var _this = _super.call(this) || this;
        _this.isWin = false;
        _this._adds = 1;
        _this._type = 0;
        _this._bet = 0;
        _this._winCoin = 0;
        return _this;
    }
    Object.defineProperty(BetArea.prototype, "type", {
        get: function () {
            return this._type;
        },
        set: function (v) {
            this._type = v;
            if (v == AreaType.Dragon)
                this._adds = 2;
            else if (v == AreaType.Tiger)
                this._adds = 2;
            else if (v == AreaType.Tie)
                this._adds = 16;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(BetArea.prototype, "bet", {
        get: function () {
            return this._bet;
        },
        set: function (value) {
            this._bet = value;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(BetArea.prototype, "winCoin", {
        get: function () {
            return this._winCoin;
        },
        set: function (v) {
            this._winCoin = v;
        },
        enumerable: true,
        configurable: true
    });
    BetArea.prototype.reset = function () {
        this.winText.visible = false;
        this.betText.visible = false;
        this._bet = 0;
        this._winCoin = 0;
    };
    BetArea.prototype.addBet = function (value) {
        this._bet += value;
        this.betText.visible = true;
        this.betText.text = this._bet.toString();
    };
    BetArea.prototype.partAdded = function (partName, instance) {
        _super.prototype.partAdded.call(this, partName, instance);
        this.addEventListener(eui.UIEvent.COMPLETE, this.uiCompHandler, this);
    };
    BetArea.prototype.childrenCreated = function () {
        _super.prototype.childrenCreated.call(this);
    };
    BetArea.prototype.uiCompHandler = function () {
        var resName = "";
        var roleName = "";
        if (this.type == AreaType.Dragon) {
            resName = "pixelcat_png";
            roleName = "cat_png";
            this.addsText.text = "1:2";
        }
        else if (this.type == AreaType.Tie) {
            resName = "pixeltie_png";
            roleName = "tie_png";
            this.addsText.text = "1:8";
        }
        else if (this.type == AreaType.Tiger) {
            resName = "pixeldog_png";
            roleName = "dog_png";
            this.addsText.text = "1:2";
        }
        this.roleImg.source = RES.getRes(resName);
        this.roleNameImg.source = RES.getRes(roleName);
        this.reset();
        this.touchChildren = false;
    };
    BetArea.prototype.settle = function (winType) {
        if (this._bet == 0)
            return;
        this.winText.visible = true;
        this.isWin = (winType == this.type);
        if (this.isWin) {
            this._winCoin = this._bet * this._adds;
            this.winText.text = "+" + this._winCoin;
        }
        else {
            this._winCoin = 0;
            this.winText.text = "-" + this._bet;
        }
    };
    BetArea.prototype.getChipPos = function () {
        return this.localToGlobal(this.width * 0.5, this.height * 0.5);
    };
    return BetArea;
}(eui.Component));
__reflect(BetArea.prototype, "BetArea", ["eui.UIComponent", "egret.DisplayObject"]);
//# sourceMappingURL=BetArea.js.map