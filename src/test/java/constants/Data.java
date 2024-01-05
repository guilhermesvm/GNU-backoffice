package constants;

import static dynamicfactory.UserData.Esportiva;
import static dynamicfactory.UserData.Eventos;
import static dynamicfactory.UserData.Marketing;
import static dynamicfactory.UserData.Master;
import static dynamicfactory.UserData.Ouvidoria;

import java.util.List;

public class Data {
	//Valid Credentials 
	public static final String validLogin = "guilherme.machado@digitalbusiness.com.br";
	public static final String validLogin2 = "gnu.teste@mailinator.com";
	public static final String validPassword = "GNU@123g";
	public static final String validCode = "000000";
	
	//Invalid Emails
	public static final String invalidLogin = "joao.batatinha@digitalbusiness.com.br";
	public static final String invalidLoginCAPSLOCK = "GUILHERME.MACHADO@DIGITALBUSINESS.com.br";
	public static final String invalidLoginCAPSLOCK2 = "GUILHERME.MACHADO@digitalbusiness.com.br";
	public static final String invalidLoginCAPSLOCK3 = "guilherme.machado@DIGITALBUSINESS.com.br";
	public static final String invalidLoginWithoutAT = "guilherme.machadodigitalbusiness.com.br";
	public static final String blankLogin = "  ";
	public static final String emptyLogin = "";
	public static final String invalidLoginWithoutExtension = "guilherme.machado@digitalbusiness";
	public static final String invalidLoginEmoji = "guilherme.machado😎@digitalbusiness.com.br";
	public static final String invalidLoginEmoji2 = "guilherme.machado@digita😎lbusiness.com.br";
	public static final String invalidLoginSpecial = "guilherme.machado#@digitalbusiness.com.br";
	public static final String invalidLoginSpecial2 = "guilherme.machado@#digitalbusiness.com.br";
	public static final String invalidLoginWithBlankChars = "guilherme machado @ digitalbusiness .com. br";
	
	//Invalid Id
	public static final String invalidId = "123124";
	
	//Invalid Passwords
	public static final String invalidPassword = "123";
	public static final String blankPassword = "  ";
	public static final String emptyPassword = "";
	
	//Invalid Codes
	public static final String invalidCode = "123456";
	public static final String blankCode = "";
	public static final String emptyCode = " ";
	
	//Invalid Name
	public static final String blankName = "";
	public static final String emptyName = "     ";
	public static final String shortName = "A";
	public static final String longName = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
	
	//HomeImages
	public static final String fileJPG = "src/test/resources/img/test.jpeg";
	public static final String filePNG = "src/test/resources/img/linux.png";
	public static final String invalidFileGIF = "src/test/resources/img/soa.gif";
	public static final String invalidFileMP3 = "src/test/resources/img/godfather.mp3";
		
	//Banners
	public static final String bannerFile = "src/test/resources/img/uniao-vantagens.png";
	public static final String bannerFile2 = "src/test/resources/img/uniao-teste.jpg";
		
	//Invalid Links
	public static final String invalidLinkEmoji = "www.😃.com";
	public static final String invalidLinkWithoutDomain = "www.google";
	public static final String invalidLinkSpecial = "Ж";
	
	//Admin Sections
	public static final List<Integer> Master =  Master();
	public static final List<Integer> Marketing =  Marketing();
	public static final List<Integer> Eventos =  Eventos();		
	public static final List<Integer> Ouvidoria =  Ouvidoria();
	public static final List<Integer> Esportiva =  Esportiva();
	
	//Invalid Tokens
	public static final String usedFirstAccessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL3VzZXJkYXRhIjoie1wiSWRcIjowLFwiTmFtZVwiOlwiRGF2aSBMdWNhIGRhIFZlaWdhXCIsXCJFbWFpbFwiOlwiYm9oZXJlMjIyNEB1YmluZXJ0LmNvbVwiLFwiQ29kZVwiOlwiMDU4NTg0XCIsXCJWYWxpZENvZGVcIjpmYWxzZSxcIlJvbGVzXCI6bnVsbCxcIlRva2VuXCI6bnVsbH0iLCJuYmYiOjE3MDM2OTk5MjEsImV4cCI6MTcwMzc4NjMyMSwiaWF0IjoxNzAzNjk5OTIxfQ.OMligixRKh__tK0sKsQ4CXo6QQHYOty0-5Ya5xp4m8I";
	public static final String emptyToken = null;
	public static final String invalidToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL3VzZXJkYXRhIjoie1wiSWRcIjozMSxcIlJvbGVzXCI6W1wibWFzdGVyXCJdLFwiTmFtZVwiOlwiR3VpbGhlcm1lIE1hY2hhZG9cIixcIkVtYWlsXCI6XCJndWlsaGVybWUubWFjaGFkb0BkaWdpdGFsYnVzaW5lc3MuY29tLmJyXCIsXCJDb2RlXCI6XCI0NjI0MjlcIixcIlRva2VuXCI6bnVsbH0iLCJuYmYiOjE3MDQyOTAwMTcsImV4cCI6MTcwNDM3NjQxNywiaWF0IjoxNzA0MjkwMDE3fQ.iLOVD5AWKGURNo5W5TEg0wFbyse2ONOrT1kfae-WqwE";
	public static final String passwordToken = "token";
	
	//Misc
	public static final String emoji = "😃";
	public static final String specialChar = "Ж";
	
	
	
	 
}
