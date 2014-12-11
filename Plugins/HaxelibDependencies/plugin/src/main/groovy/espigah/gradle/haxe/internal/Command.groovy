
package espigah.gradle.haxe.internal;
import org.gradle.api.tasks.Exec;
import espigah.gradle.haxe.internal.Constants;
import espigah.gradle.haxe.LibObject;
import java.util.regex.Matcher 
import org.codehaus.groovy.runtime.ProcessGroovyMethods;
import java.lang.ProcessBuilder;
class Command {
	Command()	{}
	
	public install(lib, version)
	{					
		def comm = haxelib(Constants.COMMAND_INSTALL) + lib
		if(version != null && version != "")
		{
			comm = comm + " " +version
		}
		
		CommandFeedback commandFeedback = execute(comm);			
		if(commandFeedback.value == "No such Project :")
		{
			commandFeedback.errorLevel = 2;
		}
		else
		if(commandFeedback.value.indexOf('No such version')!=-1)
		{
			commandFeedback.errorLevel = 2;
		}		
		else if(commandFeedback.value.indexOf("You already have") != -1)
		{
			def versionIndex =  commandFeedback.value.indexOf("version");
			def versionLastIndex =  versionIndex+8;
			def versionLib = commandFeedback.value.substring(versionLastIndex,versionLastIndex+5);			
			commandFeedback.errorLevel = 0;
			execute(setVersion(lib,versionLib))
		}
		return commandFeedback
	}
	
	
	public String info(lib)
	{
		haxelib(Constants.COMMAND_INFO, lib)
	}
	
	public String dev(lib, version)
	{
		haxelib(Constants.COMMAND_DEV, lib, version)
	}
	
	public String setVersion(lib, version)
	{
		return haxelib(Constants.COMMAND_SET, lib, version)
	}
	
	private String haxelib(command, lib, version)
	{
		String comand = haxelib(command, lib)
		if(version)
		{ 
			comand = comand +" " +  version
		}
		 
		 return comand;
	}
	
	private String haxelib(command, lib)
	{
		return haxelib(command) + lib;
	}
	
	private String haxelib(command)
	{
		return "cmd /s " + "/c "+ Constants.COMMAND +" "+ command +" "
	}
	
	public config (libCOnfig)
	{
		def libVersion;
		def libName;
		if(libCOnfig instanceof  LibObject)
		{
			libVersion = libCOnfig.version;
			libName  = libCOnfig.name; 
		}
		else
		{
			libVersion = libCOnfig.@version;
			libName  = libCOnfig.@name; 
		}
		return install(libName,libVersion);
	}
	
	
	
	
	private CommandFeedback execute(arguments)
	{
		println "EXE->" + arguments
		def proc = runCommand(arguments,4000);		
		CommandFeedback commandFeedback = new CommandFeedback();	
		
		println "CommandFeedback"
		
		//proc.text.eachLine {println it}
		def returnCode = proc[1];
		def returnValue = proc[0];
		
		println returnCode + "-=- "+returnValue
			
		if(returnCode == 0 && returnValue == 0 &&  returnValue != "Blocking")
		{
			returnValue = proc.in.text;
		}
		else
		{
			if(returnValue == "Blocking")
			{
				commandFeedback.errorLevel = -1;
			}
			else
			{
				commandFeedback.errorLevel = 1;
			}
			
		}
		commandFeedback.value = proc[0];
		commandFeedback.error = proc[1];
		return commandFeedback;		
	}
	
	//http://blogs.sheelapps.com/2013/03/run-system-command-in-groovy.html
	private runCommand (String cmdLine = "" , long wait = 5000 ) 
	{
	 
	 def sout = new StringBuffer() 
	 def serr = new StringBuffer() 
	 def piped = cmdLine.split("\\|") as List
	 Process process 
	 piped.each { cmd ->
		cmd = cmd.trim()
		def cmdList = cmd.split(' ') as List
		if(process)
		   process = process.pipeTo(cmdList.execute())
		 else
		   process = cmdList.execute()
	 }
	 process.consumeProcessOutput(sout,serr)  
	 process.waitForOrKill(wait)
	 if(serr)
		 println "serr"+serr     
	 if(sout)
		 println "sout"+sout  
			
		/*def t = new Timer()
		t.runAfter(3500){
			println 'Took too long'
			System.exit(2)
		}*/
		// println "-"+process.getOutputStream()
		// println "-"+process.isAlive()
		// println "process.exitValue()="+process.exitValue()
		// assert value != process.exitValue()
		//println "process.in.text="+process.in.text
		return [sout,serr];
	}
	
	private String trimVersionInfo(inText)
	{
		def versionIndex =inText.lastIndexOf("Version:")		
		def libVersion = inText.subSequence(versionIndex+9,versionIndex+15).trim()
		
		return libVersion;
	}
	
}


