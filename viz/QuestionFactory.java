package viz;
import java.util.*;
import Interpreter.*;

public class QuestionFactory implements UpdateReasons
{

	public Question getStartQuestion()
	{
		ArrayList<String> varNames = Global.getSymbolTable().getCurrentVarNamesArray();
		Random r = new Random();
		int choose = r.nextInt(varNames.size());
		String varName = varNames.get(choose);
		while (Global.getCurrentSymbolTable().getVariable(varName).getIsArray())
		{
			varName = varNames.get(r.nextInt(varNames.size()));
		}
		int questionType = r.nextInt(2); //Change to 3 once 3rd question type.
		Question question = null;
		switch (questionType)
		{
			case 0:
				question = new FIBQuestion("What will the value of "
						 + varName + " be after the main function returns?");
				break;
			case 1:
				question = new TFQuestion("After the main function returns, the value of "
				 + varName + " will be ");
				 break;
				 
			default:
			
		}
		question.setVariable(varName);
		try
		{
			question.setValue(Global.getSymbolTable().get(varName));
		}
		catch (Exception e)
		{
			System.out.println(e);
		}

		return question;
	}
	public Question getCallQuestion(String functionName, HashMap<String, String> pa)
	{
		Question question = null;
		String varName = null;
		Random r = new Random();
		String prev = "";
		for (String s : pa.values())
		{
			if (s.equals(prev))
			{
				varName = s;
				break;
			}
		}
		if (varName == null)
		{
			int choose = r.nextInt(pa.size());
			Object[] args = pa.values().toArray();
			varName = (String)args[choose];
		}
		if (Global.getCurrentSymbolTable().getVariable(varName).getIsArray())
		{
			String questionText = "";
			ArrayList<String> answers = new ArrayList<String>();
			ArrayList<String> choices = new ArrayList<String>();
			int whichQ = r.nextInt(2);
			if (whichQ == 0)
			{
				questionText = "Which evaluation strategies would reflect changes to arguments while still in " + functionName + "'s scope?";
				answers.add("Call by Reference");
				choices.add("Call by Value");
				choices.add("Call by Copy-Restore");
			}
			else
			{
				questionText = "Which evaluation strategies would reflect changes to arguments after " + functionName + " has returned?";
				answers.add("Call by Copy-Restore");
				answers.add("Call by Reference");
				choices.add("Call by Value");
			}
			question = new MSQuestion(questionText);
			for (String s : answers)
			{
				((MSQuestion)question).addAnswer(((MSQuestion)question).addChoice(s));
			}
			for (String s : choices)
			{
				((MSQuestion)question).addChoice(s);
			}
			
		}
		else
		{
			String scopeHint = " from the global scope ";
			int var = 0;
			try
			{
				var = Global.getFunction("main").getSymbolTable().get(varName, true);
			}
			catch (Exception e)
			{
				System.out.println(e);
			}
			//System.out.println("QQQQ " + var);
			if (var != -255)
			{
				scopeHint = " declared in main ";
			}
		
			int choose = r.nextInt(2);
			switch (choose)
			{
				case 0:
					question = new FIBQuestion("What will the value of " + varName + 
					scopeHint + " be after " + functionName + " returns?");
					break;
				case 1:
					question = new TFQuestion("After " + functionName + 
						" returns, the value of " + varName + scopeHint + 
						" will be ");
						break;
			}

			question.setVariable(varName);
		}			
		return question;
	}
	
	
	public Question getAssignmentQuestion(int lineNumber, String varName)
	{
		Question question = null;
		boolean gotAnArg = false;
		if (Global.getCurrentSymbolTable().getVariable(varName).isParam())
		{
			Random r = new Random();
			int choose = r.nextInt(2);
			
			if (choose == 0)  //should be choose == 0
			{
				String argName = Global.getCurrentParamToArg().get(varName);
				Interpreter.Variable arg =  Global.getFunction("main").getSymbolTable().getVariable(argName);
				//System.out.println("Argname: " + argName);
				if (!arg.getIsArray())
				{
					varName = argName;
					gotAnArg = true;
				}
			}
				
		}
		question = new FIBQuestion(
				"What will be the value of " + 
				(gotAnArg ? "the variable passed as an argument " : "") +
				varName + 
				" after the current line executes?");
		question.setVariable(varName);
		question.aboutArg = gotAnArg;

		return question;
	}
	
	public Question getAssignmentQuestion(int lineNumber, String varName, int index)
	{
		//System.out.println("Getting an array question");
		int i = 0;
		int localVal = 0;
		FIBQuestion question = null;
		try
		{
			localVal = Global.getCurrentSymbolTable().get(varName, true);
		
			//This stuff does nothing now.  FIXME
			if (localVal != -255)//Exists locally
			{
				int globalVal = Global.getCurrentSymbolTable().get(varName, true);
				int mainVal = Global.getFunction("main").getSymbolTable().get(varName, true);
				//Exists globally and /could/ index the array
				if (globalVal != -255 && globalVal >= 0 && globalVal < 5)
				{
					Random r = new Random();
					int prob = r.nextInt(2);
					if (prob == 0)
					{
						i = globalVal;  
					}
					else if (mainVal != -255 && mainVal >= 0 && mainVal < 5)
					{
						prob = r.nextInt(2);
						if (prob == 0)
						{
							i = mainVal;
						}
					}
				}
			}
			else
			{
				i = index;
			}
			String scopeHint = " in the current scope ";
		
			question = new FIBQuestion("What will be the value of " + varName + "[" + index + "] " + scopeHint + "after the current line executes?");
			question.setVariable(varName);
			question.setIndex(index);
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		return question;
	}
/*	
	
	public Question addCallQuestion(HashMap<String, String> paramToArg, String funName)
	{
		String param = getRandomMember(paramToArg.keySet());
		String arg = paramToArg.get(param);
		TFQuestion question;
		//question hinges on only 1 arg per var
		question = new TFQuestion("If the evaluation strategy were call by reference instead of call by value, the value of " +
			arg + " would have changed when " + funName + " returns.");
		question.setExpectedValue(Global.getCurrentSymbolTable().get(arg));
		System.out.println("In callQuestion: " + Global.getCurrentSymbolTable().getName());
		question.setAnswer(false);
		question.setExpectedValue(Global.getCurrentSymbolTable().get(param));
		funcQuestions.put(param, question);
		
		
		return question;
		
	}
	
	public Question addAssignmentQuestion(HashMap<String, String> paramArgMap, String leftHandVar)
	{
		String arg = paramArgMap.get(leftHandVar);
		TFQuestion question;
		if (arg != null)
		{
			question = new TFQuestion("The variable passed as a parameter , " + arg + " will have it's value changed after the next line of code executes");
			question.setAnswer(false);
		}
		else if (Global.getSymbolTable().get(leftHandVar) != -255)
		{
			
			question = new TFQuestion("In the Global scope, " + leftHandVar + "'s value will have changed after the next line of code executes");
			if (Global.getCurrentSymbolTable().get(leftHandVar, true) != -255)
			{
				question.setAnswer(false);
			}
			else
			{
				question.setAnswer(true);
			}
		}
		else
		{
			question = new TFQuestion("If the evaluation strategy were pass by reference instead of pass by value, the outcome of executing the next line of code be the same.");
			question.setAnswer(true);
		}
		return question;
	}
	
	public Question addEndQuestion()
	{
		return null;
	}
	
	public void addAnswers(int lineNumber, int reason)
	{
		System.out.println("Adding answers");
		switch (reason)
		{
			case UPDATE_REASON_END:
				System.out.println("Ok, we're updating at the end");
				for (String key : endQuestions.keySet())
				{
					System.out.println(endQuestions.size());
					Question q = endQuestions.get(key);

					int answer = Global.getSymbolTable().get(key);
					((FIBQuestion)q).addAnswer(answer + "");
					System.out.println(q.getText() + "answer: " + answer);
				}

				break;

			case UPDATE_REASON_ASSIGNMENT:
				System.out.println("Leave assignment");
				for (String key : funcQuestions.keySet())
				{
					Question q = funcQuestions.get(key);
					
					int answer = Global.getCurrentSymbolTable().get(key);
					if (((TFQuestion)q).getExpectedValue() != answer)
					{
						if (!((TFQuestion)q).getAnswer())
						{
							((TFQuestion)q).flipAnswer();
						}
					}
				}
			case UPDATE_REASON_LEAVEFUN:

				System.out.println("Leave func question updates");
				for (String key : callQuestions.keySet())
				{
					Question q = callQuestions.get(key);
					if (q.getClass().getName().equals("viz.TFQuestion"))
					{
						int answer = Global.getCurrentSymbolTable().get(key);
						if (((TFQuestion)q).getExpectedValue() != answer)
						{
							System.out.println("We werent' expecting that:\n" + key + "'s value was " +
							answer + " and we thought it should be " + ((TFQuestion)q).getExpectedValue());
							System.out.println("Current answer: " + ((TFQuestion)q).getAnswer());
							if(!((TFQuestion)q).getAnswer())
							{
								((TFQuestion)q).flipAnswer();
							}
						}
						System.out.println("New answer is " + ((TFQuestion)q).getAnswer());
					}
				}
				break;
			default:
				//System.out.println("No answers to add for this reason");
		}		
	}
	
	private String getGlobalVar()
	{
		HashSet<String> varNames = Global.getSymbolTable().getCurrentVarNames();
		return getRandomMember(varNames);
	}
	
	private void getArgVar()
	{
		
	}
	
	private String getRandomMember(HashSet<String> varNames)
	{
		Random r = new Random();
		return (String)varNames.toArray()[r.nextInt(varNames.size())];
	}
	
	private String getRandomMember(Set<String> varNames)
	{
		Random r = new Random();
		return (String)varNames.toArray()[r.nextInt(varNames.size())];
	}
	
*/		
}

