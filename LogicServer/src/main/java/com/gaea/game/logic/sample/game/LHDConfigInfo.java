
 package com.gaea.game.logic.sample.game;

 import com.dyuproject.protostuff.Tag;
 import com.gaea.game.core.ws.WSMessage;
 import javax.annotation.Generated;
 import java.util.*;

/**
 * Auto generate by "Python tools"
 * 
 * @Date 2017-09-01 17:04:29
 */
 @WSMessage
 @Generated("Python tools")
 public class LHDConfigInfo extends GameConfigInfo{
    
    public static LHDConfigInfo getLHDConfigInfo(int sid) {
        return (LHDConfigInfo)factory.getSample(sid);
    }

    public static LHDConfigInfo newLHDConfigInfo(int sid) {
        return (LHDConfigInfo)factory.newSample(sid);
    }
 	@Tag(4)
	// 龙赔率
	public float longOdds;
	@Tag(5)
	// 和赔率
	public float heOdds;
	@Tag(6)
	// 虎赔率
	public float huOdds;
	@Tag(7)
	// 准备时间
	public int readyTime;
	@Tag(8)
	// 押注时间
	public int betTime;
	@Tag(9)
	// 结算时间
	public int billTime;
	@Tag(10)
	// 休息时间
	public int idelTime;
	@Tag(11)
	// 总时间
	public int totalTime;
	@Tag(12)
	// 最小押注
	public int minBet;
	@Tag(13)
	// 最大押注
	public int maxBet;
	@Tag(14)
	// 默认押注
	public int defaultBet;
	@Tag(15)
	// 筹码
	public List<Integer> chips;

 }
