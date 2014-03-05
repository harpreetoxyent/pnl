using System;
public class Compile 
{
	public static void Main(string[] arg)
	{
		Scanner scanner = new Scanner(arg[0]);		
		Parser parser = new Parser(scanner);
		parser.Parse();
		Console.Write(parser.errors.count + " errors detected");
	}
}

