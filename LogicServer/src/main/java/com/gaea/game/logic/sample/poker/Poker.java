
 package com.gaea.game.logic.sample.poker;

 import com.dyuproject.protostuff.Tag;
 import com.gaea.game.base.ws.WSMessage;
 import com.gaea.game.logic.sample.Sample;
 import com.gaea.game.logic.sample.SampleFactory;
 import com.gaea.game.logic.sample.impl.SampleFactoryImpl;

 import javax.annotation.Generated;

/**
 * Auto generate by "Python tools"
 * 
 * @Date 2017-08-28 17:56:30
 */
 @WSMessage
 @Generated("Python tools")
 public class Poker extends Sample{
    public static SampleFactory<Poker> factory = new SampleFactoryImpl<>();
    public static Poker getPoker(int sid) {
        return factory.getSample(sid);
    }

    public static Poker newPoker(int sid) {
        return factory.newSample(sid);
    }
 	@Tag(3)
	// 花色
	public int type;
	@Tag(4)
	// 点数
	public int value;

 }
