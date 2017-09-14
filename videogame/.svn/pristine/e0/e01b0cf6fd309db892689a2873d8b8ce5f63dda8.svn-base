/**
 * Created by yangsong on 2014/11/22.
 */
class App {
    /**
     * 请求服务器使用的用户标识
     * @type {string}
     */
    public static ProxyUserFlag:string = "";
  
    /**
     * ProtoFile
     * @type {null}
     */
    public static Proto:any = null;
  
    public static get GlobalData():GlobalData {
        return GlobalData.getInstance();
    }

    /**
     * Http请求
     * @type {Http}
     */
    public static get Http():Http {
        return Http.getInstance();
    }

   

    /**
     * 调试工具
     * @type {DebugUtils}
     */
    public static get DebugUtils():DebugUtils {
        return DebugUtils.getInstance();
    }

    /**
     * 服务器返回的消息处理中心
     * @type {MessageCenter}
     */
    public static get MessageCenter():MessageCenter {
        return MessageCenter.getInstance(0);
    }

    /**
     * 统一的计时器和帧刷管理类
     * @type {TimerManager}
     */
    public static get TimerManager():TimerManager {
        return TimerManager.getInstance();
    }

  
    /**
     * 数学计算工具类
     * @type {MathUtils}
     */
    public static get MathUtils():MathUtils {
        return MathUtils.getInstance();
    }

    /**
     * 随机数工具类
     * @type {RandomUtils}
     */
    public static get RandomUtils():RandomUtils {
        return RandomUtils.getInstance();
    }

  
   

    /**
     * Effect工具类
     */
    public static get EffectUtils():EffectUtils {
        return EffectUtils.getInstance();
    }

  

    /**
     * 音乐管理类
     */
    public static get SoundManager():SoundManager {
        return SoundManager.getInstance();
    }

  
   


    /**
     * 资源加载工具类
     */
    public static get ResourceUtils():ResourceUtils {
        return ResourceUtils.getInstance();
    }

   


    /**
     * 消息通知中心
     */
    private static _notificationCenter:MessageCenter;

    public static get NotificationCenter():MessageCenter {
        if (App._notificationCenter == null) {
            App._notificationCenter = new MessageCenter(1);
        }
        return App._notificationCenter;
    }


    /**
     * 分帧处理类
     * @returns {any}
     * @constructor
     */
    public static get DelayOptManager():DelayOptManager {
        return DelayOptManager.getInstance();
    }

    /**
     * 数组工具类
     * @returns {any}
     * @constructor
     */
    public static get ArrayUtils():ArrayUtils {
        return ArrayUtils.getInstance();
    }


    /**
     * 初始化函数
     * @constructor
     */
    public static Init():void {
        //开启调试
        App.DebugUtils.isOpen(App.GlobalData.IsDebug);
        App.DebugUtils.setThreshold(5);
        
    }
}
