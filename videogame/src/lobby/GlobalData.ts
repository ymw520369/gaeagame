class GlobalData extends BaseClass{
	public playerId:string;
	public roomId:string;
	public gameId:string;
	public IsDebug:boolean = true;

	public gameData:any = null;
	public role:any = null;
	public isLogin = false;
	public isResume = false;
	public constructor() {
		super();
	}
}