
 package com.gaea.game.logic.sample.poker;

 import com.gaea.game.logic.sample.Sample;
 import com.gaea.game.logic.sample.SampleFactory;
 import com.gaea.game.logic.sample.impl.SampleFactoryImpl;
 import com.dyuproject.protostuff.Tag;
 import com.gaea.game.core.ws.WSMessage;
 import javax.annotation.Generated;

 /**
 * Auto generate by "Python tools"
 * 
 * @Date 2017-09-01 17:04:29
 */
 @WSMessage
 @Generated("Python tools")
 public class Poker extends Sample{
    public static SampleFactory<Poker> factory = new SampleFactoryImpl<>();
    public static Poker getPoker(int sid) {
        return (Poker)factory.getSample(sid);
    }

    public static Poker newPoker(int sid) {
        return (Poker)factory.newSample(sid);
    }
 	@Tag(3)
	// 花色
	public int type;
	@Tag(4)
	// 点数
	public int value;

 }
