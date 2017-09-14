
 package com.gaea.game.logic.sample.game;

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
 public class GameConfigInfo extends Sample{
    public static SampleFactory<GameConfigInfo> factory = new SampleFactoryImpl<>();
    public static GameConfigInfo getGameConfigInfo(int sid) {
        return (GameConfigInfo)factory.getSample(sid);
    }

    public static GameConfigInfo newGameConfigInfo(int sid) {
        return (GameConfigInfo)factory.newSample(sid);
    }
 	@Tag(3)
	// 游戏类型
	public int gameType;

 }
