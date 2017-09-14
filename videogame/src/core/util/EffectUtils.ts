/**
 * Created by yangsong on 2014/12/3.
 * 各种效果工具类
 */
class EffectUtils extends BaseClass {
    /**
     * 构造函数
     */
    public constructor() {
        super();
    }

    /**
     * 类似mac上图标上下抖动的效果
     * @param obj 要抖动的对象，使用
     * @param initY 要抖动的对象的初始Y值，原始位置
     * @example eval(macIconShake("this.btnIcon", 100));
     * @returns {string} 返回的是一个要执行代码的字符串，通过eval执行
     */
    public macIconShake(obj:egret.DisplayObject, initY:number):void {
        //抖动频率[时间，移动距离]，可修改
        egret.Tween.get(obj)
            .to({y:initY-50,scaleX:1.6,scaleY:1.6},400).to({y:initY,scaleX:1,scaleY:1},400)
            .to({y:initY-35,scaleX:1.4,scaleY:1.4},400).to({y:initY,scaleX:1,scaleY:1},400)
            .to({y:initY-15,scaleX:1.2,scaleY:1.2},400).to({y:initY,scaleX:1,scaleY:1},400)
            .to({y:initY-5,scaleX:1.1,scaleY:1.1},400).to({y:initY,scaleX:1,scaleY:1},400);
    }

    /**
     * 开始闪烁
     * @param obj
     */
    public startFlicker(obj:egret.DisplayObject, alphaTime:number):void {
        obj.alpha = 1;
        egret.Tween.get(obj).to({"alpha": 0}, alphaTime).to({"alpha": 1}, alphaTime).call(this.startFlicker, this, [obj]);
    }

    /**
     * 停止闪烁
     * @param obj
     */
    public stopFlicker(obj:egret.DisplayObject):void {
        egret.Tween.removeTweens(obj);
    }
}
