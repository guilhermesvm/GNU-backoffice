package utils;

public class Data {
	//Valid Credentials 
	public static final String ValidLogin = "guilherme.machado@digitalbusiness.com.br";
	public static final String ValidPassword = "GNU@123g";
	public static final String ValidCode = "000000";
	
	//Invalid Emails
	public static final String InvalidLogin = "joao.batatinha@digitalbusiness.com.br";
	public static final String InvalidLoginCAPSLOCK = "GUILHERME.MACHADO@DIGITALBUSINESS.com.br";
	public static final String InvalidLoginCAPSLOCK2 = "GUILHERME.MACHADO@digitalbusiness.com.br";
	public static final String InvalidLoginCAPSLOCK3 = "guilherme.machado@DIGITALBUSINESS.com.br";
	public static final String InvalidLoginWithoutAT = "guilherme.machadodigitalbusiness.com.br";
	public static final String BlankLogin = "  ";
	public static final String EmptyLogin = "";
	public static final String InvalidLoginWithoutExtension = "guilherme.machado@digitalbusiness";
	public static final String InvalidLoginEmoji = "guilherme.machado☎@digitalbusiness.com.br";
	public static final String InvalidLoginEmoji2 = "guilherme.machado@digita☎lbusiness.com.br";
	public static final String InvalidLoginSpecial = "guilherme.machado#@digitalbusiness.com.br";
	public static final String InvalidLoginSpecial2 = "guilherme.machado@#digitalbusiness.com.br";
	public static final String InvalidLoginWithBlankChars = "guilherme.machado @ digitalbusiness .com.br";
	
	//Invalid Passwords
	public static final String InvalidPassword = "123";
	public static final String BlankPassword = "  ";
	public static final String EmptyPassword = "";
	
	//Invalid Codes
	public static final String InvalidCode = "123456";
	public static final String BlankCode = "";
	public static final String EmptyCode = " ";
	
	//Invalid Id
	public static final String InvalidId = "123124";
	
	//Tokens
	public static final String firstAccessToken = "token";
	public static final String usedFirstAccessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL3VzZXJkYXRhIjoie1wiSWRcIjowLFwiTmFtZVwiOlwiRGF2aSBMdWNhIGRhIFZlaWdhXCIsXCJFbWFpbFwiOlwiYm9oZXJlMjIyNEB1YmluZXJ0LmNvbVwiLFwiQ29kZVwiOlwiMDU4NTg0XCIsXCJWYWxpZENvZGVcIjpmYWxzZSxcIlJvbGVzXCI6bnVsbCxcIlRva2VuXCI6bnVsbH0iLCJuYmYiOjE3MDM2OTk5MjEsImV4cCI6MTcwMzc4NjMyMSwiaWF0IjoxNzAzNjk5OTIxfQ.OMligixRKh__tK0sKsQ4CXo6QQHYOty0-5Ya5xp4m8I";
	public static final String emptyToken = null;
	public static final String passwordToken = "token";
	
	//Img
	public static final String file = "src/test/resources/img/test.jpeg";
	public static final String bannerFile = "src/test/resources/img/uniao-vantagens.png";
	public static final String invalidFile = "src/test/resources/img/soa.gif";
	public static final String invalidFile2 = "src/test/resources/img/godfather.mp3";
	
	
	 
}
