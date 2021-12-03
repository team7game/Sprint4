There are two options to run this code:

Option 1 (Easier Method):
1. Download zip of Sprint4 repository.
2. Unzip the folder.
3. The unziped folder should have two jar files within it.
4. Double click on Sprint4.jar to run the code.

Option 2 (Backup Method):
1. Download zip of Sprint4 repository.
2. Unzip the folder.
3. In command prompt, navigate to the src folder within the Sprint4 folder.
4. Compile the code using the following command:
	javac -cp ".;com/resources/postgresql-42.2.24.jar" -d . com/team7/*.java
5. Run the code using the following command:
	java -splash:com/img/Photon.jpg -cp ".;com/resources/postgresql-42.2.24.jar" com/team7/App
	
Notes:
1. This code was tested using the latest version of java (17.0.1).
2. Our database currently has player IDs 1 through 5 populated.