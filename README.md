# Telegram_PostMaker
	Its JavaFX Desktop App for Telegram. 
There is a bot which can Automatically Post in you groups\chanels.
	For trying this programm, you 
can use the bundle files, that are in the folder Telegram_PostMaker/out/artifacts/ - 
there you can find two folders that includes ".jar" files and every kind of files for 
every kind Operating system. 
 And You can see the result @Posts_Maker_bot. (Yon need to add it to your group\chanel )
In order to connect your own bot to this app, you need to do do next steps:
   - For makeing yor own bot and geting its token 
   - you need write to @BotFather in Telegram.
   - After yor need to add bot token to project
   - After you need to open this project in Intellij idea.
   - Then you must to find BOT.class and add inside "botToken" and "Username" which you got from  @BotFather.
For finsh you just need to rebuild the project and get ".jar" file, which you can to use))Good Luck.))
	I must warn you that there are some problems with building JavaFx projects.
It happens because: JavaFX is not part of the JDK anymore.
In order to make of the project - executable file - ".jar" of ".exe" I know three ways to do it: 
  - An easier way is to install old version of Java SDK Java8 or older,
   then to include it to Project sdk in your idea,
   and rebuild with it. 
Second way you see here -  https://stackoverflow.com/questions/52467561/intellij-cant-recognize-javafx-11-with-openjdk-11/52470141#52470141
And other way you can see here https://www.youtube.com/watch?v=HGHu-SzL-5E&t=266s.
