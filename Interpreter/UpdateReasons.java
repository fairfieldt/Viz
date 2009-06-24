package Interpreter;

public interface UpdateReasons
{
	public static final int UPDATE_REASON_BEGIN = 0;
	public static final int UPDATE_REASON_CALL = 1;
	public static final int UPDATE_REASON_FUNCTION = 2;
	public static final int UPDATE_REASON_STATEMENT = 3;
	public static final int UPDATE_REASON_END = 4;
	public static final int UPDATE_REASON_LEAVEFUN = 5;
	public static final int UPDATE_REASON_ASSIGNMENT = 6;
}
