var __reflect = (this && this.__reflect) || function (p, c, t) {
    p.__class__ = c, t ? t.push(c) : t = [c], p.__types__ = p.__types__ ? t.concat(p.__types__) : t;
};
var __extends = (this && this.__extends) || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
};
var AreaType;
(function (AreaType) {
    AreaType[AreaType["None"] = 0] = "None";
    AreaType[AreaType["Dragon"] = 1] = "Dragon";
    AreaType[AreaType["Tie"] = 2] = "Tie";
    AreaType[AreaType["Tiger"] = 3] = "Tiger";
})(AreaType || (AreaType = {}));
var DragonController = (function (_super) {
    __extends(DragonController, _super);
    function DragonController() {
        var _this = _super.call(this) || this;
        _this._dragonValue = 0;
        _this._tigerValue = 0;
        _this._chipIndex = 0;
        _this._chipValue = 10;
        _this._chipPool = [];
        _this.credit = 10000;
        _this._winArea = 0;
        _this._isBet = false;
        _this.addEventListener(eui.UIEvent.COMPLETE, _this.uiCompHandler, _this);
        _this.skinName = "resource/game/dragon/skin/dragonSkin.exml";
        return _this;
    }
    DragonController.prototype.uiCompHandler = function () {
        this._chipValueArray = [10, 50, 100, 1000, 10000];
        this._chipArray = [this.chip_0, this.chip_1, this.chip_2, this.chip_3, this.chip_4];
        this._areaArray = [this.dragonArea, this.tieArea, this.tigerArea];
        var chip;
        for (var i = 0; i < 5; i++) {
            chip = this._chipArray[i];
            chip.addEventListener(egret.TouchEvent.TOUCH_TAP, this.doSelectChip, this);
        }
        this.twin_png.visible = false;
        this.dwin_png.visible = false;
        this.he_png.visible = false;
        for (var j = 0; j < 3; j++) {
            var area = this._areaArray[j];
            area.addEventListener(egret.TouchEvent.TOUCH_TAP, this.doTouchArea, this);
            area.type = j + 1;
        }
        this._cardData = RES.getRes("config_json");
        this.test();
    };
    DragonController.prototype.test = function () {
        this._timer = new egret.Timer(1000, 0);
        this._timer.addEventListener(egret.TimerEvent.TIMER, this.timerFunc, this);
        this._timer.addEventListener(egret.TimerEvent.TIMER_COMPLETE, this.timerComFunc, this);
        this._timer.start();
    };
    DragonController.prototype.timerFunc = function (event) {
        var time = event.target.currentCount;
        //egret.log(time.toString());
        if (time == 1) {
            Toast.launch("please bet !");
            this.sendCard();
        }
        else if (time > 1 && time < 21) {
            this.timeText.text = (22 - time).toString();
        }
        else if (time == 21) {
            this._isBet = false;
            Toast.launch("stop bet !");
        }
        else if (time == 23) {
            this.timeText.text = "";
            this.openCard(this.card_1, this.card_1_back);
            this.openCard(this.card_2, this.card_2_back);
        }
        else if (time == 24) {
            this.showResult();
        }
        else if (time == 26) {
            this.settleAccounts();
        }
        else if (time == 30) {
            this.reset();
        }
        else if (time == 33) {
            this._timer.reset();
            this._timer.start();
        }
    };
    DragonController.prototype.timerComFunc = function (event) {
        egret.log("timerComFunc count" + event.target.currentCount);
    };
    DragonController.prototype.doSelectChip = function (evt) {
        var chip = evt.target;
        chip.selected = true;
        var i = this._chipArray.indexOf(chip);
        if (i != this._chipIndex) {
            this._chipArray[this._chipIndex].selected = false;
            this._chipIndex = i;
        }
        this._chipValue = this._chipValueArray[i];
    };
    DragonController.prototype.doTouchArea = function (evt) {
        if (!this._isBet)
            return;
        if (this.credit < this._chipValue)
            return;
        var area = evt.target;
        var end = area.getChipPos();
        var chip = this._chipArray[this._chipIndex];
        var start = chip.localToGlobal(chip.width * 0.5, chip.height * 0.5);
        this.flyChip(start, end, area);
    };
    DragonController.prototype.openCard = function (front, back) {
        back.scaleX = 1;
        front.scaleX = 0;
        egret.Tween.get(back).to({ scaleX: 0 }, 400, egret.Ease.backIn).call(function () {
            egret.Tween.get(front).to({ scaleX: 1 }, 400, egret.Ease.backOut);
        });
    };
    DragonController.prototype.closeCard = function (front, back) {
        egret.Tween.get(front).to({ scaleX: 0 }, 400, egret.Ease.backIn).call(function () {
            egret.Tween.get(back).to({ scaleX: 1 }, 400, egret.Ease.backOut);
        });
    };
    DragonController.prototype.sendCard = function () {
        this._isBet = true;
        var i = this.RandomNumBoth(1, 52);
        //egret.log(this._cardData[i].res);
        this.card_1.source = RES.getRes(this._cardData[i].res);
        this._dragonValue = this._cardData[i].value;
        i = this.RandomNumBoth(1, 52);
        //egret.log(this._cardData[i].res);
        this.card_2.source = RES.getRes(this._cardData[i].res);
        this._tigerValue = this._cardData[i].value;
    };
    DragonController.prototype.reset = function () {
        this.closeCard(this.card_1, this.card_1_back);
        this.closeCard(this.card_2, this.card_2_back);
        this.twin_png.visible = false;
        this.dwin_png.visible = false;
        this.he_png.visible = false;
        for (var _i = 0, _a = this._areaArray; _i < _a.length; _i++) {
            var area = _a[_i];
            area.reset();
        }
    };
    DragonController.prototype.RandomNumBoth = function (Min, Max) {
        var Range = Max - Min;
        var Rand = Math.random();
        var num = Min + Math.round(Rand * Range);
        return num;
    };
    DragonController.prototype.showResult = function () {
        if (this._dragonValue > this._tigerValue) {
            this.dwin_png.visible = true;
        }
        else if (this._dragonValue == this._tigerValue) {
            this.he_png.visible = true;
        }
        else {
            this.twin_png.visible = true;
        }
    };
    DragonController.prototype.flyChip = function (start, end, area) {
        var _this = this;
        var img = this.getChipByPool();
        this.addChild(img);
        img.x = start.x;
        img.y = start.y;
        egret.Tween.get(img).to({ x: end.x, y: end.y }, 500, egret.Ease.backOut).call(function () {
            _this.removeChild(img);
            _this._chipPool.push(img);
            area.addBet(_this._chipValue);
            _this.changeCredit(-_this._chipValue);
        });
    };
    DragonController.prototype.settleAccounts = function () {
        if (this._dragonValue > this._tigerValue) {
            this._winArea = AreaType.Dragon;
        }
        else if (this._dragonValue == this._tigerValue) {
            this._winArea = AreaType.Tie;
        }
        else {
            this._winArea = AreaType.Tiger;
        }
        var sum = 0;
        for (var _i = 0, _a = this._areaArray; _i < _a.length; _i++) {
            var area = _a[_i];
            area.settle(this._winArea);
            sum += area.winCoin;
        }
        this.changeCredit(sum);
    };
    DragonController.prototype.getChipByPool = function () {
        var chip;
        if (this._chipPool.length > 0) {
            chip = this._chipPool.pop();
        }
        else {
            chip = new eui.Image();
            chip.source = RES.getRes("coin s_png");
        }
        return chip;
    };
    DragonController.prototype.changeCredit = function (value) {
        this.credit += value;
        this.creditText.text = this.credit.toString();
    };
    return DragonController;
}(eui.Component));
__reflect(DragonController.prototype, "DragonController");
//# sourceMappingURL=DragonController.js.map